package de.addiks.intellijsdi.intellijsdi.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.event.DocumentEvent;
import de.addiks.intellijsdi.intellijsdi.services.WindowAutoResizeService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class DocumentListener implements com.intellij.openapi.editor.event.DocumentListener {

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {
        ApplicationManager.getApplication().getService(WindowAutoResizeService.class).onDocumentModified(
                event.getDocument()
        );
    }
}
