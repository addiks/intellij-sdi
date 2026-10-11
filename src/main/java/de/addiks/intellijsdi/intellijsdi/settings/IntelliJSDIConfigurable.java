package de.addiks.intellijsdi.intellijsdi.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBBox;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
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
    private JBCheckBox shouldResizeWindows;
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
        var settings = IntellijSDISettings.getInstance().getState();

        panel = new JPanel();
        var layout = new BoxLayout(panel, BoxLayout.Y_AXIS); // new GridLayout(0, 1);

        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(100, 100));


        shouldMoveEditorsIntoOwnWindows = new JBCheckBox(
                "Move newly opened text-editors into their own windows"
        );
        shouldMoveToolWindowsIntoOwnWindows = new JBCheckBox(
                "Move newly opened tool-windows into their own windows"
        );
        shouldResizeWindows = new JBCheckBox(
                "Should automatically resize windows"
        );

        panel.add(shouldMoveEditorsIntoOwnWindows);
        panel.add(shouldMoveToolWindowsIntoOwnWindows);
        panel.add(shouldResizeWindows);

        minimumWindowWidthField = addNumberFieldWithLabel("Minimum window width", settings.minimumWindowWidth);
        minimumWindowHeightField = addNumberFieldWithLabel("Minimum window height", settings.minimumWindowHeight);
        maximumWindowWidthField = addNumberFieldWithLabel("Maximum window width", settings.maximumWindowWidth);
        maximumWindowHeightField = addNumberFieldWithLabel("Maximum window height", settings.maximumWindowHeight);

        panel.add(Box.createGlue());

        reset();
        return panel;
    }

    private JBTextField addNumberFieldWithLabel(String label, int initialValue) {
        var hbox = new JBBox(BoxLayout.X_AXIS);
        var field = new JBTextField(Integer.toString(initialValue));
        field.setInputVerifier(ONLY_NUMBERS);
        field.setMaximumSize(new Dimension(150, 100));
        hbox.add(field);
        hbox.add(new JBLabel(label));
        hbox.setMaximumSize(new Dimension(600, 100));
        panel.add(hbox);
        return field;
    }

    @Override
    public boolean isModified() {
        var settings = IntellijSDISettings.getInstance().getState();
        return settings.shouldMoveEditorsIntoOwnWindows != shouldMoveEditorsIntoOwnWindows.isSelected()
                || settings.shouldMoveToolWindowsIntoOwnWindows != shouldMoveToolWindowsIntoOwnWindows.isSelected()
                || settings.shouldResizeWindows != shouldResizeWindows.isSelected()
                || settings.minimumWindowWidth != Integer.parseInt(minimumWindowWidthField.getText())
                || settings.minimumWindowHeight != Integer.parseInt(minimumWindowHeightField.getText())
                || settings.maximumWindowWidth != Integer.parseInt(maximumWindowWidthField.getText())
                || settings.maximumWindowHeight != Integer.parseInt(maximumWindowHeightField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        var settings = IntellijSDISettings.getInstance().getState();
        settings.shouldMoveEditorsIntoOwnWindows = shouldMoveEditorsIntoOwnWindows.isSelected();
        settings.shouldMoveToolWindowsIntoOwnWindows = shouldMoveToolWindowsIntoOwnWindows.isSelected();
        settings.shouldResizeWindows = shouldResizeWindows.isSelected();
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
        shouldResizeWindows.setSelected(settings.shouldResizeWindows);
        minimumWindowWidthField.setText(Integer.toString(settings.minimumWindowWidth));
        minimumWindowHeightField.setText(Integer.toString(settings.minimumWindowHeight));
        maximumWindowWidthField.setText(Integer.toString(settings.maximumWindowWidth));
        maximumWindowHeightField.setText(Integer.toString(settings.maximumWindowHeight));
    }
}
