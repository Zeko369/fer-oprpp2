package hr.fer.oprpp2.client;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App extends JFrame {
    private final String host;
    private final int port;

    private final String username;

    private JTextField input;
    private final SocketClient socketClient;
    private final JTextArea content;
    private final List<MessageRow> messages = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java -jar chat-client.jar <host> <port> <username>");
            System.exit(1);
        }

        String position = "0x0";
        if (args.length == 4) {
            if (args[3].matches("[0-9]*x[0-9]*")) {
                position = args[3];
            } else {
                System.err.println("Position should be in format <width>x<height>");
            }
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];

        SocketClient socketClient = new SocketClient(host, port);
        try {
            socketClient.init();
            boolean ok = socketClient.join(username);
            if (!ok) {
                System.err.println("Couldn't connect to server");
                System.exit(1);
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        String finalPosition = position;
        SwingUtilities.invokeLater(() -> {
            JFrame app = new App(host, port, username, finalPosition, socketClient);
            app.setVisible(true);
        });
    }

    public App(String host, int port, String username, String position, SocketClient socketClient) {
        FlatLightLaf.setup();

        this.host = host;
        this.port = port;
        this.username = username;
        this.socketClient = socketClient;

        Integer[] pos = Arrays.stream(position.split("x")).map(Integer::parseInt).toArray(Integer[]::new);
        this.setLocation(pos[0], pos[1]);

        this.content = new JTextArea();
        this.content.setEditable(false);

        this.setTitle(String.format("Chat client: %s", this.username));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.initUI();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                socketClient.disconnect();
            }
        });

        this.pack();

        this.socketClient.listen((message) -> {
            this.messages.add(new MessageRow(message.getIndex(), message.getAuthor(), message.getText()));
            this.rerender();
            return null;
        });
    }

    private void rerender() {
        String newContent = this.messages.stream().map((m) -> m.toString(String.format("%s:%d", this.host, this.port))).collect(Collectors.joining());
        this.content.setText(newContent);
        this.content.setCaretPosition(newContent.length());
    }

    private void initUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        this.input = new JTextField();
        this.input.addActionListener(this.sendMessage);
        cp.add(this.input, "North");
        cp.add(new JScrollPane(this.content), "Center");
    }

    // This was disabled since lambdas have access to properties before they are initialized in constructor
    @SuppressWarnings("Convert2Lambda")
    private final ActionListener sendMessage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField input = (JTextField) e.getSource();
            String text = input.getText().trim();
            input.setText("");

            new SendWorker(text).execute();
        }
    };

    private class SendWorker extends SwingWorker<Void, Void> {
        private final String text;

        public SendWorker(String text) {
            this.text = text;
        }

        @Override
        protected Void doInBackground() throws Exception {
            App.this.input.setEnabled(false);
            if (!App.this.socketClient.sendChatMessage(this.text)) {
                System.err.println("Couldn't send message");
                // shutdown
            }

            return null;
        }

        @Override
        protected void done() {
            App.this.input.setEnabled(true);
            App.this.input.requestFocus();
        }
    }
}
