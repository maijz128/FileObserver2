package xyz.maijz128.service.impl;


import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import xyz.maijz128.service.FileObserver;
import java.io.File;


public class FileObserverImpl extends FileObserverBase implements FileObserver {

    boolean isDebug = false;

    @Override
    void start(String path) {
        watch2(path);
    }


    void  watch2(String path){
        try {
            final FileAlterationObserver observer = new FileAlterationObserver(path);
            observer.addListener(new FileAlterationListenerAdaptor() {

                @Override
                public final void onDirectoryChange(final File directory) {
                    if(isDebug) System.out.println("onDirectoryChange: " + directory);
                    triggerOnModify(directory.toString());
                }

                @Override
                public final void onFileChange(final File file) {
                    if(isDebug) System.out.println("onFileChange: " + file);
                    triggerOnModify(file.toString());
                }

            });
            FileAlterationMonitor monitor =
                    new FileAlterationMonitor(2000, observer);
            monitor.addObserver(observer);
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
