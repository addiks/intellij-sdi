package de.addiks.intellijsdi.intellijsdi.listeners;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ToolWindowListener implements ToolWindowManagerListener {

    private static final Logger log = LoggerFactory.getLogger(ToolWindowListener.class);

    public void toolWindowsRegistered(@NotNull List<String> ids, @NotNull ToolWindowManager toolWindowManager) {
        log.info("FOO BAR BAZ " + ids);
    }

    public void stateChanged(@NotNull ToolWindowManager toolWindowManager,
                              @NotNull ToolWindow toolWindow,
                              @NotNull ToolWindowManagerListener.ToolWindowManagerEventType changeType) {
        toolWindow.setType(ToolWindowType.WINDOWED, null);
    }


}
