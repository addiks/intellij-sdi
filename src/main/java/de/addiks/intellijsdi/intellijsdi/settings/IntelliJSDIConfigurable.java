package de.addiks.intellijsdi.intellijsdi.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBCheckBox;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class IntelliJSDIConfigurable implements Configurable {

    private JPanel panel;
    private JBCheckBox shouldMoveEditorsIntoOwnWindows;
    private JBCheckBox shouldMoveToolWindowsIntoOwnWindows;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Window Management (SDI)";
    }

    @Override
    public @Nullable JComponent createComponent() {
        panel = new JPanel(new GridLayout(0, 1));

        shouldMoveEditorsIntoOwnWindows = new JBCheckBox("Should move editors into their own windows");
        shouldMoveToolWindowsIntoOwnWindows = new JBCheckBox("Should move tool-windows into their own windows");

        panel.add(shouldMoveEditorsIntoOwnWindows);
        reset();

        return panel;
    }

    @Override
    public boolean isModified() {
        var settings = IntellijSDISettings.getInstance().getState();
        return settings.shouldMoveEditorsIntoOwnWindows != shouldMoveEditorsIntoOwnWindows.isSelected()
                && settings.shouldMoveToolWindowsIntoOwnWindows != shouldMoveToolWindowsIntoOwnWindows.isSelected();
    }

    @Override
    public void apply() throws ConfigurationException {
        var settings = IntellijSDISettings.getInstance().getState();
        settings.shouldMoveEditorsIntoOwnWindows = shouldMoveEditorsIntoOwnWindows.isSelected();
        settings.shouldMoveToolWindowsIntoOwnWindows = shouldMoveToolWindowsIntoOwnWindows.isSelected();
    }

    @Override
    public void reset() {
        var settings = IntellijSDISettings.getInstance().getState();
        shouldMoveEditorsIntoOwnWindows.setSelected(settings.shouldMoveEditorsIntoOwnWindows);
        shouldMoveToolWindowsIntoOwnWindows.setSelected(settings.shouldMoveToolWindowsIntoOwnWindows);
    }
}
