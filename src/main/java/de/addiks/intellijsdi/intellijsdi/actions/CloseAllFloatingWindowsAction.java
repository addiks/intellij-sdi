package de.addiks.intellijsdi.intellijsdi.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.impl.ToolWindowManagerImpl;
import org.jetbrains.annotations.NotNull;

public class CloseAllFloatingWindowsAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project == null) {
            return;
        }
        if (FileEditorManager.getInstance(project) instanceof FileEditorManagerImpl fileEditorManagerImpl) {
            fileEditorManagerImpl.closeOpenedEditors();
        }
        if (ToolWindowManager.getInstance(project) instanceof ToolWindowManagerImpl toolWindowManager) {
            for (String toolWindowId : toolWindowManager.getToolWindowIds()) {
                toolWindowManager.hideToolWindow(toolWindowId, false);
            }
        }
    }
}
