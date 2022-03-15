package client;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App extends JFrame {
    private final String servername;
    private final String username;

    private final List<MessageRow> messages = new ArrayList<>();
    private final JTextArea content;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar chat-client.jar <servername> <username>");
            System.exit(1);
        }

        String position = "0x0";
        if (args.length == 3) {
            if (args[2].matches("[0-9]*x[0-9]*")) {
                position = args[2];
            } else {
                System.err.println("Position should be in format <width>x<height>");
            }
        }

        String finalPosition = position;
        SwingUtilities.invokeLater(() -> new App(args[0], args[1], finalPosition).setVisible(true));
    }

    public App(String servername, String username) {
        this(servername, username, "0x0");
    }

    public App(String servername, String username, String position) {
        FlatLightLaf.setup();

        this.servername = servername;
        this.username = username;

        this.setTitle(String.format("Chat client: %s", this.username));
        Integer[] pos = Arrays.stream(position.split("x")).map(Integer::parseInt).toArray(Integer[]::new);
        this.setLocation(pos[0], pos[1]);

        this.content = new JTextArea();
        this.content.setEditable(false);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.initUI();

        this.pack();
    }

    private void rerender() {
        String newContent = this.messages.stream().map((m) -> m.toString(this.servername)).collect(Collectors.joining());
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

        this.messages.add(new MessageRow(0, "FooBar", text));
        this.rerender();
    };
}
