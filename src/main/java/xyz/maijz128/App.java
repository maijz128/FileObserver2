package xyz.maijz128;

import xyz.maijz128.service.FileObserver;
import xyz.maijz128.service.WebNotifier;
import xyz.maijz128.service.impl.FileObserverImpl;
import xyz.maijz128.service.impl.WebNotifierImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    private Config config;

    private WebNotifier webNotifier;

    private FileObserver fileObserver;


    public static void main(String[] args) throws Exception {
        new App();
    }

    public App() throws Exception {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                initWebSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                initFileServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t2.start();
    }

    private void initFileServer() throws Exception {
        HttpServerImpl httpServer = new HttpServerImpl();
        httpServer.run();
    }

    private void initWebSocket() throws Exception {
        config = Config.getSingleton();

        String payload = config.getPayload();
        int port = config.getWebSocketPort();

        fileObserver = new FileObserverImpl();
        webNotifier = new WebNotifierImpl(port, payload);

        init();
    }


    private void init() {
        Path p = Paths.get(config.getPath());
        if (!Files.exists(p)) {
            try {
                Files.createDirectory(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileObserver.onModify(this::onModify);
        fileObserver.watch(config.getPath());
    }

    private void onModify(String file) {
        System.out.println("onModify: " + file);
        webNotifier.notifyFileModified();
    }

}
