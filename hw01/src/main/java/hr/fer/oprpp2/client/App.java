package hr.fer.oprpp2.client;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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

    private SocketClient socketClient = null;
    private final List<MessageRow> messages = new ArrayList<>();
    private final JTextArea content;

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

        String finalPosition = position;
        SwingUtilities.invokeLater(() -> {
            try {
                new App(args[0], Integer.parseInt(args[1]), args[2], finalPosition).setVisible(true);
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
        });
    }

    public App(String host, int port, String username, String position) throws UnknownHostException, SocketException {
        FlatLightLaf.setup();

        this.host = host;
        this.port = port;
        this.username = username;

        this.socketClient = new SocketClient(this.host, this.port);
        this.socketClient.init();
        this.socketClient.join(this.username);

        Integer[] pos = Arrays.stream(position.split("x")).map(Integer::parseInt).toArray(Integer[]::new);
        this.setLocation(pos[0], pos[1]);

        this.content = new JTextArea();
        this.content.setEditable(false);

        this.setTitle(String.format("Chat client: %s", this.username));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.initUI();

        this.pack();

        this.socketClient.listen();
    }

    private void rerender() {
        String newContent = this.messages.stream().map((m) -> m.toString(String.format("%s:%d", this.host, this.port))).collect(Collectors.joining());
        this.content.setText(newContent);
    }

    private void initUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JTextField input = new JTextField();
        input.addActionListener(this.sendMessage);
        cp.add(input, "North");
        cp.add(new JScrollPane(this.content), "Center");
    }

    private final ActionListener sendMessage = e -> {
        JTextField input = (JTextField) e.getSource();
        String text = input.getText();
        input.setText("");

        this.socketClient.sendMessage(text);
    };
}
