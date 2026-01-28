import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Objects;

public abstract class SLyDServer {
    protected String Domain = null;
    public final int DEFAULT_PORT = 6052;
    protected Integer[] Ports = {DEFAULT_PORT};
    private void Start(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        while (true) {


            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread(() -> {

                try {
                    if (Objects.equals(in.readLine(), "domain")) {
                        out.println(Domain);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                        wait(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(clientSocket.isClosed()){
                        Thread.currentThread().interrupt();
                    }
                    responce();



            }).start();
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public final void StartPorts(){
        for(int i : Ports){
            new Thread(() -> Start(i)).start();
        }
    }
    public abstract void responce();
}