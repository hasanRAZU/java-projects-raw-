import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 9000;
    private static final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    /* broadcast text message*/
    static void broadcast(String sender, String message, byte[] profileImage) {
        for (ClientHandler c : clients) {
            try {
                c.sendMessage(sender, message, profileImage);
            } catch (IOException e) {
                clients.remove(c);
                c.close();
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private String name;
        private byte[] profileImage;

        ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            } catch (IOException e) {
                close();
            }
        }

        @Override
        public void run() {
            try {
                // receive name
                name = in.readUTF();

                // receive profile image
                int imgLen = in.readInt();
                profileImage = new byte[imgLen];
                in.readFully(profileImage);

                broadcast("Server", name + " joined the chat.", null);

                // listen for messages
                while (true) {
                    String msg = in.readUTF();
                    broadcast(name, msg, profileImage);
                }
            } catch (IOException e) {
                System.out.println(name + " disconnected.");
            } finally {
                close();
                clients.remove(this);
                broadcast("Server", name + " left the chat.", null);
            }
        }

        void sendMessage(String sender, String message, byte[] img) throws IOException {
            synchronized (out) {
                out.writeUTF(sender);
                out.writeUTF(message);
                if (img != null) {
                    out.writeBoolean(true);
                    out.writeInt(img.length);
                    out.write(img);
                } else {
                    out.writeBoolean(false);
                }
                out.flush();
            }
        }

        void close() {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
