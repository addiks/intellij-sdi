package de.addiks.intellijsdi.intellijsdi.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.*;
import org.jspecify.annotations.NonNull;

@Service(Service.Level.APP)
@State(
        name = "IntellijSDISettings",
        storages = @Storage("addiks_intellij_sdi.xml")
)
public final class IntellijSDISettings implements PersistentStateComponent<IntellijSDISettings.State> {

    public static class State {
        public boolean shouldMoveEditorsIntoOwnWindows = false;
        public boolean shouldMoveToolWindowsIntoOwnWindows = false;
        public boolean shouldResizeWindows = false;
        public int minimumWindowWidth = 100;
        public int minimumWindowHeight = 100;
        public int maximumWindowWidth = 1200;
        public int maximumWindowHeight = 800;
    }

    private @NonNull State state = new State();

    public static @NonNull IntellijSDISettings getInstance() {
        return ApplicationManager.getApplication().getService(IntellijSDISettings.class);
    }

    public static boolean shouldMoveEditorsIntoOwnWindows() {
        return getInstance().getState().shouldMoveEditorsIntoOwnWindows;
    }

    public static boolean shouldMoveToolWindowsIntoOwnWindows() {
        return getInstance().getState().shouldMoveToolWindowsIntoOwnWindows;
    }

    public static boolean shouldResizeToolWindows() {
        return getInstance().getState().shouldResizeWindows;
    }

    public static int minimumWindowWidth() {
        return getInstance().getState().minimumWindowWidth;
    }

    public static int minimumWindowHeight() {
        return getInstance().getState().minimumWindowHeight;
    }

    public static int maximumWindowWidth() {
        return getInstance().getState().maximumWindowWidth;
    }

    public static int maximumWindowHeight() {
        return getInstance().getState().maximumWindowHeight;
    }

    @Override
    public @NonNull State getState() {
        return state;
    }

    @Override
    public void loadState(IntellijSDISettings.@NonNull State state) {
        this.state = state;
    }

}
