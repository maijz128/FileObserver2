package xyz.maijz128.service.impl;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import xyz.maijz128.service.WebSocketService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collection;


public class WebSocketServiceImpl implements WebSocketService {

    private WebSocketServerImpl socketServer;


    @Override
    public void sendAll(String payload) {
        if(this.socketServer == null) return;
        Collection<WebSocket> webSockets = this.socketServer.getConnections();
        webSockets.forEach(e -> e.send(payload));
    }

    @Override
    public void stop() throws InterruptedException {
        if (this.socketServer != null) this.socketServer.stop(1000);
    }

    @Override
    public void run(int port) throws InterruptedException, IOException {
        WebSocketImpl.DEBUG = false;

        if (this.socketServer != null) {
            this.socketServer.stop(1000);
        }

        this.socketServer = new WebSocketServerImpl(port);
        this.socketServer.start();
        System.out.println("WebSocketServer started on port: " + this.socketServer.getPort());
    }


    class WebSocketServerImpl extends WebSocketServer {
        public WebSocketServerImpl(int port) throws UnknownHostException {
            super(new InetSocketAddress(port));
        }

        public WebSocketServerImpl(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            //conn.send("Welcome to the server!"); //This method sends a message to the new client
//        broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
            System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
//        broadcast(conn + " has left the room!");
            System.out.println(conn + " has left the room!");
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
//        broadcast(message);
//        System.out.println(conn + ": " + message);
        }

        @Override
        public void onMessage(WebSocket conn, ByteBuffer message) {
//        broadcast(message.array());
//        System.out.println(conn + ": " + message);
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
            if (conn != null) {
                // some errors like port binding failed may not be assignable to a specific websocket
            }
        }

        @Override
        public void onStart() {
            System.out.println("Server started!");
        }
    }

}