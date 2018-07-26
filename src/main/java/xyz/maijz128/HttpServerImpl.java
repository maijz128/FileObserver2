package xyz.maijz128;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServerImpl {

    private String URI_HEADER = "/file";

    //启动服务，监听来自客户端的请求
    public void run() throws Exception {
        int port = Config.getSingleton().getFileServerPort();
        String basePath = Config.getSingleton().getPath();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(URI_HEADER, new TestHandler(URI_HEADER, basePath));
        server.start();

        System.out.println("FileServer started on port: " + port);
        System.out.println("Folder: " + basePath);
        System.out.println("FileServer started!");
    }

    static class TestHandler implements HttpHandler{

        String uriHeader;
        String basePath;

        public TestHandler(String uriHeader, String basePath){
            this.uriHeader = uriHeader;
            this.basePath = basePath;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI uri = exchange.getRequestURI();
            String filename = uri.getPath().replaceFirst(uriHeader, "");

            String path = this.basePath;
            File file = Paths.get(path, filename).toFile();
            if(file.exists()){
                writeStream(file, exchange);
            }else {
                exchange.sendResponseHeaders(404, 0);
                exchange.close();
            }
        }

        private void sendMessage(HttpExchange exchange, String response) throws IOException {
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private void writeStream(File file, HttpExchange exchange) throws IOException {

            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int length = inputStream.read(data);
            inputStream.close();


            String cType = Files.probeContentType(file.toPath());
            exchange.getResponseHeaders().set("Content-Type", cType);
            exchange.getResponseHeaders().set("Content-Length", Integer.toString(length));
            exchange.sendResponseHeaders(200, 0);

            OutputStream stream = exchange.getResponseBody();
            stream.write(data);
            stream.flush();
            stream.close();
        }
    }

}
