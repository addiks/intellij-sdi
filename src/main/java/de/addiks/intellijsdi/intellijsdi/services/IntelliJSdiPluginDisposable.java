package de.addiks.intellijsdi.intellijsdi.services;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import org.jetbrains.annotations.NotNull;

@Service({Service.Level.APP, Service.Level.PROJECT})
public final class IntelliJSdiPluginDisposable implements Disposable {

    public static @NotNull Disposable getInstance() {
        return ApplicationManager.getApplication().getService(IntelliJSdiPluginDisposable.class);
    }

    @Override
    public void dispose() {
    }
}
