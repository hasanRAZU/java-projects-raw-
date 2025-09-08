import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.nio.file.*;

public class Client2 {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private byte[] profileImage;

    private JFrame frame;
    private JPanel chatPanel;
    private JTextField inputField;

    public Client2(String host, int port) throws Exception {
        // get name
        name = JOptionPane.showInputDialog("Enter your name:");
        if (name == null || name.trim().isEmpty()) name = "Guest";

        // choose profile picture
        JFileChooser chooser = new JFileChooser();
        JOptionPane.showMessageDialog(null, "Select a profile picture");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            profileImage = Files.readAllBytes(chooser.getSelectedFile().toPath());
        } else {
            // default blank image
            profileImage = new byte[0];
        }

        socket = new Socket(host, port);
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        // send name and profile image
        out.writeUTF(name);
        out.writeInt(profileImage.length);
        out.write(profileImage);
        out.flush();

        createUI();

        new Thread(this::listen).start();
    }

    private void createUI() {
        frame = new JFrame("Messenger Chat - " + name);
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(chatPanel);

        inputField = new JTextField();
        JButton sendBtn = new JButton("Send");
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        if (msg.isEmpty()) return;
        try {
            out.writeUTF(msg);
            out.flush();
            inputField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to send message.");
        }
    }

    private void listen() {
        try {
            while (true) {
                String sender = in.readUTF();
                String msg = in.readUTF();
                boolean hasImg = in.readBoolean();
                byte[] img = null;
                if (hasImg) {
                    int len = in.readInt();
                    img = new byte[len];
                    in.readFully(img);
                }
                addMessage(sender, msg, img);
            }
        } catch (IOException e) {
            addMessage("System", "Disconnected from server.", null);
        }
    }

    private void addMessage(String sender, String msg, byte[] img) {
        SwingUtilities.invokeLater(() -> {
            JPanel bubble = new JPanel();
            bubble.setLayout(new BorderLayout());
            bubble.setBorder(new EmptyBorder(5,5,5,5));

            JLabel textLabel = new JLabel("<html><body style='width:200px'>" + msg + "</body></html>");
            textLabel.setOpaque(true);
            textLabel.setBorder(new EmptyBorder(10,10,10,10));

            boolean isMine = sender.equals(name);
            if (isMine) {
                textLabel.setBackground(new Color(179, 229, 252)); // light blue
                bubble.add(textLabel, BorderLayout.EAST);
            } else {
                textLabel.setBackground(new Color(220, 220, 220)); // grey
                JPanel left = new JPanel(new BorderLayout());
                if (img != null && img.length > 0) {
                    ImageIcon icon = new ImageIcon(img);
                    Image scaled = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    JLabel avatar = new JLabel(new ImageIcon(scaled));
                    left.add(avatar, BorderLayout.NORTH);
                }
                left.add(textLabel, BorderLayout.CENTER);
                bubble.add(left, BorderLayout.WEST);
            }

            chatPanel.add(bubble);
            chatPanel.revalidate();
            chatPanel.repaint();
        });
    }

    public static void main(String[] args) throws Exception {
        new Client2("localhost", 9000);
    }
}
