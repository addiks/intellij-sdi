package de.addiks.intellijsdi.intellijsdi.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBTextField;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class IntelliJSDIConfigurable implements Configurable {

    private static final InputVerifier ONLY_NUMBERS = new InputVerifier() {
        @Override
        public boolean verify(JComponent input) {
            String text = ((JTextField) input).getText();
            return text.matches("\\d+");
        }
    };

    private JPanel panel;
    private JBCheckBox shouldMoveEditorsIntoOwnWindows;
    private JBCheckBox shouldMoveToolWindowsIntoOwnWindows;
    private JBTextField minimumWindowWidthField;
    private JBTextField minimumWindowHeightField;
    private JBTextField maximumWindowWidthField;
    private JBTextField maximumWindowHeightField;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Window Management (SDI)";
    }

    @Override
    public @Nullable JComponent createComponent() {
        panel = new JPanel(new GridLayout(0, 1));

        shouldMoveEditorsIntoOwnWindows = new JBCheckBox(
                "Move newly opened text-editors into their own windows"
        );
        shouldMoveToolWindowsIntoOwnWindows = new JBCheckBox(
                "Move newly opened tool-windows into their own windows"
        );

        minimumWindowWidthField = new JBTextField("Minimum width of editor-windows");
        minimumWindowWidthField.setInputVerifier(ONLY_NUMBERS);

        minimumWindowHeightField = new JBTextField("Minimum height of editor-windows");
        minimumWindowHeightField.setInputVerifier(ONLY_NUMBERS);

        maximumWindowWidthField = new JBTextField("Maximum width of editor-windows");
        maximumWindowWidthField.setInputVerifier(ONLY_NUMBERS);

        maximumWindowHeightField = new JBTextField("Maximum height of editor-windows");
        maximumWindowHeightField.setInputVerifier(ONLY_NUMBERS);

        panel.add(shouldMoveEditorsIntoOwnWindows);
        panel.add(shouldMoveToolWindowsIntoOwnWindows);
        panel.add(minimumWindowWidthField);
        panel.add(minimumWindowHeightField);
        panel.add(maximumWindowWidthField);
        panel.add(maximumWindowHeightField);
        reset();

        return panel;
    }

    @Override
    public boolean isModified() {
        var settings = IntellijSDISettings.getInstance().getState();
        return settings.shouldMoveEditorsIntoOwnWindows != shouldMoveEditorsIntoOwnWindows.isSelected()
                && settings.shouldMoveToolWindowsIntoOwnWindows != shouldMoveToolWindowsIntoOwnWindows.isSelected()
                && settings.minimumWindowWidth != Integer.parseInt(minimumWindowWidthField.getText())
                && settings.minimumWindowHeight != Integer.parseInt(minimumWindowHeightField.getText())
                && settings.maximumWindowWidth != Integer.parseInt(maximumWindowWidthField.getText())
                && settings.maximumWindowHeight != Integer.parseInt(maximumWindowHeightField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        var settings = IntellijSDISettings.getInstance().getState();
        settings.shouldMoveEditorsIntoOwnWindows = shouldMoveEditorsIntoOwnWindows.isSelected();
        settings.shouldMoveToolWindowsIntoOwnWindows = shouldMoveToolWindowsIntoOwnWindows.isSelected();
        settings.minimumWindowWidth = Integer.parseInt(minimumWindowWidthField.getText());
        settings.minimumWindowHeight = Integer.parseInt(minimumWindowHeightField.getText());
        settings.maximumWindowWidth = Integer.parseInt(maximumWindowWidthField.getText());
        settings.maximumWindowHeight = Integer.parseInt(maximumWindowHeightField.getText());
    }

    @Override
    public void reset() {
        var settings = IntellijSDISettings.getInstance().getState();
        shouldMoveEditorsIntoOwnWindows.setSelected(settings.shouldMoveEditorsIntoOwnWindows);
        shouldMoveToolWindowsIntoOwnWindows.setSelected(settings.shouldMoveToolWindowsIntoOwnWindows);
        minimumWindowWidthField.setText(Integer.toString(settings.minimumWindowWidth));
        minimumWindowHeightField.setText(Integer.toString(settings.minimumWindowHeight));
        maximumWindowWidthField.setText(Integer.toString(settings.maximumWindowWidth));
        maximumWindowHeightField.setText(Integer.toString(settings.maximumWindowHeight));
    }
}
