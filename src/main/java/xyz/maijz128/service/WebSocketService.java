package xyz.maijz128.service;

import java.io.IOException;

public interface WebSocketService {
    void run(int port) throws Exception;
    void stop() throws Exception;
    void sendAll(String payload);
}
