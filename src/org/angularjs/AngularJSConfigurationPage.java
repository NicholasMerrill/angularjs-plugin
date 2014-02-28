package org.angularjs;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by johnlindquist on 9/23/13.
 */
public class AngularJSConfigurationPage implements SearchableConfigurable {
    private JCheckBox includeWhitespaceBetweenBracesCheckBox;
    private JPanel myPanel;
    private JCheckBox enableAutomaticBraceInsertionCheckBox;
    private JComboBox bracesComboBox;

    @NotNull
    @Override
    public String getId() {
        return "editor.preferences.AngularJS";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "AngularJS";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        includeWhitespaceBetweenBracesCheckBox.setSelected(AngularJSConfig.object$.getWhiteSpace());
        includeWhitespaceBetweenBracesCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                boolean selected = includeWhitespaceBetweenBracesCheckBox.isSelected();
                AngularJSConfig.object$.setWhiteSpace(selected);
            }
        });

        enableAutomaticBraceInsertionCheckBox.setSelected(AngularJSConfig.object$.getBraceEnabled());
        enableAutomaticBraceInsertionCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                boolean selected = enableAutomaticBraceInsertionCheckBox.isSelected();
                AngularJSConfig.object$.setBraceEnabled(selected);
            }
        });

        bracesComboBox.setSelectedItem(AngularJSConfig.object$.getBraceCharactersSettings());
        if (AngularJSConfig.object$.getLeftBraceCharacter() == '{')
            bracesComboBox.setSelectedIndex(0);
        else if (AngularJSConfig.object$.getLeftBraceCharacter() == '[')
            bracesComboBox.setSelectedIndex(1);
        bracesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                int index = bracesComboBox.getSelectedIndex();
                char left, right;
                switch (index) {
                    case 0:
                        left = '{';
                        right = '}';
                        break;
                    case 1:
                        left = '[';
                        right = ']';
                        break;
                    default:
                        throw new IndexOutOfBoundsException();
                }
                AngularJSConfig.object$.setLeftBraceCharacter(left);
                AngularJSConfig.object$.setRightBraceCharacter(right);
            }
        });


        return myPanel;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
