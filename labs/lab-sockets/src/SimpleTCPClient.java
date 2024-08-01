import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SimpleTCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public void start(String serverIp, int serverPort) throws IOException {
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        Thread readThread = new Thread(() -> {
            try {
                while (true) {
                    String response = input.readUTF();
                    System.out.println("Servidor: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread writeThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            try {
                while (true) {
                    String msg = scanner.nextLine();
                    output.writeUTF(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                scanner.close();
            }
        });

        readThread.start();
        writeThread.start();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverIp = "0.0.0.0";
        int serverPort = 6666;
        try {
            SimpleTCPClient client = new SimpleTCPClient();
            client.start(serverIp, serverPort);
            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}