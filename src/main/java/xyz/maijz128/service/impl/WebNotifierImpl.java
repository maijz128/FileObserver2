package xyz.maijz128.service.impl;


import xyz.maijz128.service.WebSocketService;
import xyz.maijz128.service.WebNotifier;

public class WebNotifierImpl implements WebNotifier {

    private WebSocketService webSocketService;


    private String payload = "IS_CHANGED";

    public WebNotifierImpl(int port, String payload) throws Exception {
        this.payload = payload;
        webSocketService = new WebSocketServiceImpl();
        webSocketService.run(port);
    }

    @Override
    public void notifyFileModified() {
        webSocketService.sendAll(payload);
    }

}
