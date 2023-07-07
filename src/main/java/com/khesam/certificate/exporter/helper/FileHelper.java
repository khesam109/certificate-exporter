package com.khesam.certificate.exporter.helper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.*;

@Singleton
public class FileHelper {

    @Inject
    public FileHelper() {}

    public Collection<File> getInterestedFiles(String rootDirectoryPath, List<String> fileExtensions) {
        return listFileTree(
                new File(rootDirectoryPath),
                fileExtensions != null ? fileExtensions : Collections.emptyList()
        );
    }

    public Collection<File> getAllFiles(String rootDirectoryPath) {
        return listFileTree(
                new File(rootDirectoryPath),
                Collections.emptyList()
        );
    }

    private Collection<File> listFileTree(File rooDirectory, List<String> fileExtensions) {
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

    private boolean isInterested(String name, List<String> fileExtensions) {
        if (fileExtensions.isEmpty()) return true;

        return fileExtensions.stream().anyMatch(
                e -> name.toLowerCase(Locale.ROOT).endsWith(e)
        );
    }
}
