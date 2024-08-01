import java.net.*;
import java.util.concurrent.*;
import java.io.*;
import java.util.*;

public class ThreadPoolTCPServer {
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String args[]) {
        try {
            ExecutorService threadPool = Executors.newFixedThreadPool(10);
            int serverPort = 6666; // the server port
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (serverSocket.isBound()) {
                System.out.println("Aguardando conexao no endereco: " + InetAddress.getLocalHost() + ":" + serverPort);
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, clients);
                clients.add(handler);
                threadPool.submit(handler);
                System.out.println("Conexao feita com: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}