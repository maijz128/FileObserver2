package xyz.maijz128;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static Config config = null;

    public static Config getSingleton() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private final String filePath = "file-observer.properties";
    private final String PRO_WEB_SOCKET_PORT = "websocket.port";
    private final String PRO_FILE_SERVER_PORT = "fileserver.port";
    private final String PRO_FOLDER = "folder";
    private final String PRO_PAYLOAD = "payload";

    private Integer webSocketPort = 8293;
    private Integer fileServerPort = 8294;
    private String folder = "dev";
    private String payload = "IS_CHANGED";
    private String path;


    private Config() {
        load();
        save();

        Path p = Paths.get(this.folder).toAbsolutePath();
        this.path = p.toString();
    }

    private void load() {
        if (!Files.exists(Paths.get(filePath))) return;
        Properties prop = new Properties();
        InputStream InputStream = null;
        try {
            InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            prop.load(InputStream);
            String webPort = prop.getProperty(PRO_WEB_SOCKET_PORT);
            String filePort = prop.getProperty(PRO_FILE_SERVER_PORT);
            String folder = prop.getProperty(PRO_FOLDER);
            String payload = prop.getProperty(PRO_PAYLOAD);

            if (notNull(webPort)) this.webSocketPort = Integer.parseInt(webPort);
            if (notNull(filePort)) this.fileServerPort = Integer.parseInt(filePort);
            if (notNull(folder)) this.folder = folder;
            if (notNull(payload)) this.payload = payload;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void save() {
        Properties prop = new Properties();
        OutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            prop.put(PRO_WEB_SOCKET_PORT, this.webSocketPort.toString());
            prop.put(PRO_FILE_SERVER_PORT, this.fileServerPort.toString());
            prop.put(PRO_FOLDER, this.folder);
            prop.put(PRO_PAYLOAD, this.payload);
            prop.store(stream, "FileObserver2 Config");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean notNull(String str) {
        return str != null && !str.isEmpty();
    }


    public int getWebSocketPort() {
        return webSocketPort;
    }

    public Integer getFileServerPort() {
        return fileServerPort;
    }

    public String getFolder() {
        return folder;
    }

    public String getPayload() {
        return payload;
    }

    public String getPath() {
        return this.path;
    }
}
