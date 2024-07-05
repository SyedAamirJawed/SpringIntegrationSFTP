package com.sftp.config;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class FileWatcherService {

    private static final String DIRECTORY_TO_WATCH = "C:/Users/dell/Desktop/Test/lookup";
    private static final Map<String, Long> lastModified = new HashMap<>();

    public static void main(String[] args) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get(DIRECTORY_TO_WATCH);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    String fileName = event.context().toString();
                    long currentTime = System.currentTimeMillis();

                    // event chaking
                    if (shouldProcessEvent(fileName, currentTime)) {
                        System.out.println("new file : " + event.kind() + " & File name is : " + fileName);
                        
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean shouldProcessEvent(String fileName, long currentTime) {
        long lastModifiedTime = lastModified.getOrDefault(fileName, 0L);
        long timeDifference = currentTime - lastModifiedTime;

        if (timeDifference > 1000) { // 1 second threshold
        	lastModified.put(fileName, currentTime);
            return true;
        }

        return false;
    }
}
