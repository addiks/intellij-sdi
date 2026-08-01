package de.addiks.intellijsdi.intellijsdi.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.*;

public class FileEditorListener implements FileEditorManagerListener {
    private static final Logger log = LoggerFactory.getLogger(FileEditorListener.class);

    private final List<VirtualFile> currentlyMoving = Collections.synchronizedList(new ArrayList<>());

    public void fileOpened(@NotNull FileEditorManager manager, @NotNull VirtualFile file) {
        if (manager instanceof FileEditorManagerImpl managerImpl) {
            EditorWindow currentWindor = managerImpl.getCurrentWindow();
            if (currentWindor == null) {
                log.warn("No current window!");
                return;
            }
            if (currentlyMoving.contains(file)) {
                return;
            }
            if (currentWindor.getOwner() != managerImpl.getMainSplitters()) {
                currentlyMoving.remove(file);
                return;
            }
            ApplicationManager.getApplication().invokeLater(() -> {
                if (currentlyMoving.contains(file)) {
                    return;
                }
                currentlyMoving.add(file);
                try {
                    managerImpl.openFileInNewWindow(file);
                    currentWindor.closeFile(file);
                } finally {
                    currentlyMoving.remove(file);
                }
            });
        }
    }
}
