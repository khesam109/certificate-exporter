package com.khesam.certificate.exporter.helper;

import java.io.File;
import java.util.*;

public class FileHelper {

    private FileHelper() {}

    public static Collection<File> listInterestedFiles(String rootDirPath, List<String> fileExtensions) {
        return listFileTree(
                new File(rootDirPath),
                fileExtensions != null ? fileExtensions : Collections.emptyList()
        );
    }

    public static Collection<File> listAllFiles(String rootDirPath) {
        return listFileTree(
                new File(rootDirPath),
                Collections.emptyList()
        );
    }

    private static Collection<File> listFileTree(File rooDirectory, List<String> fileExtensions) {
        Set<File> fileTree = new HashSet<>();
        if (rooDirectory == null || rooDirectory.listFiles() == null) {
            return Collections.emptyList();
        }
        for (File entry : Objects.requireNonNull(rooDirectory.listFiles())) {
            if (entry.isFile() && isInterested(entry.getName(), fileExtensions)) {
                fileTree.add(entry);
            } else {
                fileTree.addAll(listFileTree(entry, fileExtensions));
            }
        }
        return fileTree;
    }

    private static boolean isInterested(String name, List<String> fileExtensions) {
        if (fileExtensions.isEmpty()) return true;

        return fileExtensions.stream().anyMatch(
                e -> name.toLowerCase(Locale.ROOT).endsWith(e)
        );
    }
}
