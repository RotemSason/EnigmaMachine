package servlets.Allies;

import EngineManager.SingleAgentManager;
import EngineManager.SingleAlliesManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer {

    //private final ServerSocket serverSocket;
    private final SingleAlliesManager singleAlliesManager;
    private Thread mainServerThread;
    private boolean isStopped;
    private List<Socket> openConnections;

    public SimpleServer(SingleAlliesManager singleAlliesManager) throws IOException {
        this.singleAlliesManager = singleAlliesManager;
        mainServerThread = null;

      //  serverSocket = new ServerSocket(port);
        isStopped = false;
        openConnections = new ArrayList<>();
    }

    public void startContestOnDifferentThread() {
        mainServerThread = new Thread(this::runServer);
        mainServerThread.start();
    }

    private void runServer() {
        try {
            singleAlliesManager.StartContest();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}