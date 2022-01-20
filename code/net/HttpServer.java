package org.domain.code.net;

import org.domain.code.App;
import org.domain.code.Convert;
import org.domain.code.Text;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private Event event;
    private ServerSocket server = null;
    private ExecutorService myExecutorService = null;
    private HashMap<String, PrintWriter> clientHttp = new HashMap<>();

    public HttpServer(Object port) {

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

    public static class Client {
        private java.net.Socket socket = null;
        private OutputStream outputStream = null;
        private String msg = Text.EMPTY;
        private BufferedReader bufferedReader = null;
        private PrintWriter writer = null;
        public Client(Object output, Object socket) {
            if (output != null && (output instanceof OutputStream) && socket != null && (socket instanceof java.net.Socket)) {
                this.outputStream = (OutputStream) output;
                this.socket = (java.net.Socket) socket;
                this.writer = new PrintWriter(this.outputStream, true);
            }
        }

        public void msg(Object msg) {
            if (msg != null) {
                this.msg = Convert.to(Convert.STRING, msg).toString();
            }
        }

        public boolean println() {
            return println(null);
        }

        public boolean println(Object data) {
            boolean result = false;
            try {

                if (this.outputStream != null) {
                    if(data != null) {
                        if(data instanceof byte[] || data instanceof InputStream) {
                            write(data);
                        } else {
                            writer.println(data.toString());
                        }
                    } else {
                        writer.println();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        public boolean write(Object data) {
            boolean result = false;
            try {
                if (this.outputStream != null) {
                    println();
                    this.outputStream.write(Convert.bytes(Convert.bytes(data)));
                    result = true;
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        public boolean close() {
            boolean result = false;
            try {
                if (this.outputStream != null && this.socket != null) {
                    this.outputStream.close();
                    this.writer.close();
                    if (!socket.isClosed()) {
                        this.socket.close();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                App.Log.send(exception);
            }
            return result;
        }

        @Override
        public String toString() {
            return msg;
        }
    }

    private class Service implements Runnable {

        private java.net.Socket socket;
        private BufferedReader in = null;
        private String msg = "";
        private Client client = null;
        private StringBuffer content = new StringBuffer();

        public Service(java.net.Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                client = new Client(socket.getOutputStream(), socket);
            } catch (Exception exception) {
            }
        }

        @Override
        public void run() {

            try {
                while ((msg = in.readLine()) != null && !msg.equals(Text.EMPTY)) {
                    content.append(msg);
                }
                client.msg(content.toString());
                out((Client) client);
            } catch (Exception exception) {
                App.Log.send(exception);
            }

        }

    }


    public interface Event {
        void out(Client client);
    }

    public void event(Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    public void out(Client client) {
        if (event != null) {
            event.out(client);
        }
    }


    public static class Socket {

    }
}
