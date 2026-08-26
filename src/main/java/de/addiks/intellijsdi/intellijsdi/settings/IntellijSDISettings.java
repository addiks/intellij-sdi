package de.addiks.intellijsdi.intellijsdi.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Service(Service.Level.APP)
@State(
        name = "IntellijSDISettings",
        storages = @Storage("addiks_intellij_sdi.xml")
)
public final class IntellijSDISettings implements PersistentStateComponent<IntellijSDISettings.State> {

    public static class State {
        public boolean shouldMoveEditorsIntoOwnWindows = false;
        public boolean shouldMoveToolWindowsIntoOwnWindows = false;
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

    @Override
    public @NonNull State getState() {
        return state;
    }

    @Override
    public void loadState(IntellijSDISettings.@NonNull State state) {
        this.state = state;
    }

}
