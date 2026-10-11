package de.addiks.intellijsdi.intellijsdi.listeners;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.wm.WindowManager;
import de.addiks.intellijsdi.intellijsdi.services.WindowAutoResizeService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import static java.awt.event.HierarchyEvent.PARENT_CHANGED;
import static java.awt.event.HierarchyEvent.SHOWING_CHANGED;

public class WindowAutoResizeListener implements EditorFactoryListener, DocumentListener {

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        Document document = editor.getDocument();
        JFrame mainWindow = WindowManager.getInstance().getFrame(editor.getProject());

        editor.getComponent().addHierarchyListener(e -> {
            if ((e.getChangeFlags() & PARENT_CHANGED) != 0 || (e.getChangeFlags() & SHOWING_CHANGED) != 0) {
                Window editorWindow = SwingUtilities.getWindowAncestor(e.getComponent());
                if (editorWindow != null && !editorWindow.equals(mainWindow)) {
                    ApplicationManager.getApplication().getService(WindowAutoResizeService.class).registerEditorWindow(
                            document,
                            editorWindow,
                            editor
                    );
                }
            }
        });
    }

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {
        ApplicationManager.getApplication().getService(WindowAutoResizeService.class).onDocumentModified(
                event.getDocument()
        );
    }

}
