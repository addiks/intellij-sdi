package de.addiks.intellijsdi.intellijsdi.services;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import de.addiks.intellijsdi.intellijsdi.settings.IntellijSDISettings;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public final class WindowAutoResizeService {
    private final Map<Document, Window> windowsByDocument = new HashMap<>();
    private final Map<Document, Editor> editorsByDocument = new HashMap<>();

    private final Map<Window, Dimension> automaticResizesPerWindow = new HashMap<>();
    private final Map<Window, AtomicBoolean> manuallyResizedWindows = new HashMap<>();

    public void registerEditorWindow(Document document, Window window, Editor editor) {
        windowsByDocument.put(document, window);
        editorsByDocument.put(document, editor);
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onWindowResized(window);
            }
        });
        scheduleWindowToContentFitting(window, editor);
    }

    public void onDocumentModified(Document document) {
        if (windowsByDocument.containsKey(document) && editorsByDocument.containsKey(document)) {
            scheduleWindowToContentFitting(windowsByDocument.get(document), editorsByDocument.get(document));
        }
    }

    private void scheduleWindowToContentFitting(Window window, Editor editor) {
        ApplicationManager.getApplication().invokeLater(() -> fitWindowToContent(window, editor));
    }

    private void fitWindowToContent(Window window, Editor editor) {
        if (IntellijSDISettings.shouldResizeToolWindows()
                && editor instanceof EditorEx editorEx
                && !wasManuallyResized(window).get()
        ) {
            Dimension extent = editorEx.getScrollPane().getViewport().getExtentSize();
            double chromeWidth = window.getWidth() - extent.getWidth();
            double chromeHeight = window.getHeight() - extent.getHeight();

            Dimension contentSize = editorEx.getContentSize();

            double newWidth = contentSize.getWidth() + chromeWidth;
            double newHeight = contentSize.getHeight() + chromeHeight;

            newWidth = Math.clamp(newWidth, IntellijSDISettings.minimumWindowWidth(), IntellijSDISettings.maximumWindowWidth());
            newHeight = Math.clamp(newHeight, IntellijSDISettings.minimumWindowHeight(), IntellijSDISettings.maximumWindowHeight());

            if (newWidth != window.getWidth() || newHeight != window.getHeight()) {
                var newSize = new Dimension((int) newWidth, (int) newHeight);
                automaticResizesPerWindow.put(window, newSize);
                window.setSize(newSize);
                window.validate();
            }
        }
    }

    private void onWindowResized(Window window) {
        Dimension newSize = window.getSize();
        if (!automaticResizesPerWindow.containsKey(window) || !automaticResizesPerWindow.get(window).equals(newSize)) {
            wasManuallyResized(window).set(true);
        }
    }

    private AtomicBoolean wasManuallyResized(Window window) {
        return manuallyResizedWindows.computeIfAbsent(window, w -> new AtomicBoolean(false));
    }

}
