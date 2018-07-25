package xyz.maijz128.service;

import java.util.function.Consumer;

public interface FileObserver {
    String getPath();
    void watch(String path);
    void onModify(Consumer<String> func);
}
