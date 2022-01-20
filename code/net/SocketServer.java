package org.domain.code.net;

import org.domain.code.App;
import org.domain.code.Convert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    private Event event;
    private ServerSocket server = null;
    private ExecutorService myExecutorService = null;
    private HashMap<String, Socket> clientSocket = new HashMap<>();
    private HashMap<Socket, String> clientIP = new HashMap<>();

    public SocketServer(Object port) {

        port = Convert.to(Convert.INT, port);
        port = port == null ? 8080 : port;
        final int ports = (int) port;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(ports);
                    //创建线程池
                    myExecutorService = Executors.newCachedThreadPool();
                    while (true) {
                        myExecutorService.execute(new Service(server.accept()));
                    }
                } catch (Exception exception) {
                    App.Log.send(exception);
                }

            }
        }).start();


    }

    public boolean close() {
        boolean result = false;
        try {
            if(!server.isClosed()) {
                server.close();
            }
            result = true;
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    private class Service implements Runnable {

        private Socket socket;
        private BufferedReader in = null;
        private String msg = "";

        public Service(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String ip = socket.getInetAddress().toString();
                Socket client = clientSocket.get(ip);
                if (client != null && !(client.isClosed() || client.isConnected())) {
                } else if (client == null) {
                    clientSocket.put(ip, socket);
                }
            } catch (Exception exception) {
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if ((msg = in.readLine()) != null) {
                        out(msg);
                        printlnAll(msg);
                    }
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
        }

        //为连接上服务端的每个客户端发送信息
        private void printlnAll(Object msg) {
            msg = msg.toString();

            System.out.println(msg);
            for (Map.Entry<String, Socket> entry : clientSocket.entrySet()) {
                Socket mSocket = clientSocket.get(entry.getKey());
                PrintWriter pout = null;
                if (clientIP.get(mSocket) != null && !(mSocket.isClosed() || mSocket.isConnected())) {
                    try {
                        pout = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8")), true);
                        pout.println(msg);
                    } catch (IOException exception) {
                        App.Log.send(exception);
                    }
                } else {
                    clientIP.remove(mSocket);
                    clientSocket.remove(clientIP.get(mSocket));
                }
            }
        }
    }



    public interface Event {
        void out(String data);
    }

    public void event(Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    public void out(String data) {
        if (event != null) {
            event.out(data);
        }
    }


}
