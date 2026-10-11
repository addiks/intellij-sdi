package de.addiks.intellijsdi.intellijsdi.services;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import de.addiks.intellijsdi.intellijsdi.settings.IntellijSDISettings;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Service
public final class WindowAutoResizeService {
    private final Map<Document, Window> windowsByDocument = new HashMap<>();
    private final Map<Document, Editor> editorsByDocument = new HashMap<>();

    public void registerEditorWindow(Document document, Window editorWindow, Editor editor) {
        windowsByDocument.put(document, editorWindow);
        editorsByDocument.put(document, editor);
        fitWindowToContent(editorWindow, editor);
    }

    public void onDocumentModified(Document document) {
        if (windowsByDocument.containsKey(document) && editorsByDocument.containsKey(document)) {
            fitWindowToContent(windowsByDocument.get(document), editorsByDocument.get(document));
        }
    }

    private void fitWindowToContent(Window editorWindow, Editor editor) {
        if (editor instanceof EditorEx editorEx) {
            Dimension extent = editorEx.getScrollPane().getViewport().getExtentSize();
            double chromeWidth = editorWindow.getWidth() - extent.getWidth();
            double chromeHeight = editorWindow.getHeight() - extent.getHeight();

            Dimension contentSize = editorEx.getContentSize();

            double newWidth = contentSize.getWidth() + chromeWidth;
            double newHeight = contentSize.getHeight() + chromeHeight;

            newWidth = Math.clamp(newWidth, IntellijSDISettings.minimumWindowWidth(), IntellijSDISettings.maximumWindowWidth());
            newHeight = Math.clamp(newHeight, IntellijSDISettings.minimumWindowHeight(), IntellijSDISettings.maximumWindowHeight());

            if (newWidth != editorWindow.getWidth() || newHeight != editorWindow.getHeight()) {
                editorWindow.setSize((int) newWidth, (int) newHeight);
                editorWindow.validate();
            }
        }
    }

}
