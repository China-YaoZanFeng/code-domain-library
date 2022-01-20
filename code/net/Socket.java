package org.domain.code.net;

import org.domain.code.App;
import org.domain.code.Convert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Socket {

    private Event event;
    private java.net.Socket socket = null;
    private PrintWriter writer = null;
    private boolean client = false;

    public Socket(Object ip, Object port) {
        port = Convert.to(Convert.INT, port);
        port = port == null ? 8080 : port;
        Object ports = port;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ip != null) {
                    try {
                        socket = new java.net.Socket(ip.toString(), (int) ports);
                        if (!(socket.isClosed() && socket.isConnected())) {
                            writer = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String line = null;
                            while ((line = bufferedReader.readLine()) != null) {
                                out(line);
                            }
                        }
                    } catch (Exception exception) {
                        App.Log.send(exception);
                    }
                }
            }
        }).start();

    }

    public boolean write(Object data) {
        boolean result = false;
        try {
            if (writer != null && data != null) {
                writer.write(data.toString());
                result = true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public void println(Object data) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        if (writer != null && data != null && !(socket.isClosed() && socket.isConnected())) {
                            writer.println(data.toString());
                            break;
                        }
                    }
                }
            }).start();

        } catch (Exception exception) {
            App.Log.send(exception);
        }

    }

    public boolean client() {
        boolean result = false;

        try {
            if (socket != null) {
                result = socket.isClosed() ? false : true;
            }
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public boolean close() {
        boolean result = false;
        try {
            if (!socket.isClosed() && writer != null) {
                socket.close();
                writer.close();
            }
            result = true;
        } catch (Exception exception) {
            App.Log.send(exception);
        }

        return result;
    }

    public static SocketServer server(Object port) {
        return new SocketServer(port);
    }

    public interface Event {
        void out(String data);
    }

    public void event(Socket.Event Event) {
        if (Event != null) {
            event = Event;
        }
    }

    private void out(String data) {
        if (event != null) {
            event.out(data);
        }
    }

}
