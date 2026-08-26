package de.addiks.intellijsdi.intellijsdi.listeners;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import de.addiks.intellijsdi.intellijsdi.settings.IntellijSDISettings;
import org.jetbrains.annotations.NotNull;

public class ToolWindowListener implements ToolWindowManagerListener {
    public void stateChanged(
            @NotNull ToolWindowManager toolWindowManager,
            @NotNull ToolWindow toolWindow,
            @NotNull ToolWindowManagerListener.ToolWindowManagerEventType changeType
    ) {
        if (IntellijSDISettings.shouldMoveToolWindowsIntoOwnWindows()) {
            toolWindow.setType(ToolWindowType.WINDOWED, null);
        }
    }
}
