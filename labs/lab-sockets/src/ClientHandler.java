import java.io.*;
import java.net.Socket;
import java.util.List;

class ClientHandler extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private List<ClientHandler> clients;

    public ClientHandler(Socket aClientSocket, List<ClientHandler> clients) {
        try {
            this.clientSocket = aClientSocket;
            this.clients = clients;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                String data = in.readUTF();
                System.out.println("Mensagem recebida: " + data);
                broadcastMessage(data);
            }
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                /* close failed */}
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            try {
                if (client != this) {
                    client.out.writeUTF(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}