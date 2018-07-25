package xyz.maijz128.service.impl;

import xyz.maijz128.service.FileObserver;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public abstract class FileObserverBase implements FileObserver {
    private String path;
    private Set<Consumer<String>> onModifyList = new HashSet<>();

    @Override
    public final String getPath() {
        return this.path;
    }

    @Override
    public final void watch(String path) {
        this.path = path;
        System.out.println("watch: " + path);
        start(path);
    }

    @Override
    public final void onModify(Consumer<String> func) {
        onModifyList.add(func);
    }

    protected final void triggerOnModify(String file){
        onModifyList.forEach(f -> f.accept(file));
    }

    abstract void start(String path);
}
