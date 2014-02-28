package org.angularjs

import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.ide.util.PropertiesComponent

/**
 * Created by johnlindquist on 7/10/13.
 */
public abstract class AngularJSConfig() {
    class object {
        var componentName: String = "AngularJSConfig"
        val addWhiteSpaceName: String = "AngularJSConfig.addWhitespaceBetweenBraces"
        val braceEnabledName: String = "AngularJSConfig.braceEnabled"
        val braceCharactersSettingsName: String = "AngularJSConfig.braceCharactersSettings"
        val leftBraceCharacterName: String = "AngularJSConfig.leftBraceCharacter"
        val rightBraceCharacterName: String = "AngularJSConfig.rightBraceCharacter"
        var whiteSpace: Boolean
            get(){
                return PropertiesComponent.getInstance()!!.getBoolean(addWhiteSpaceName, false)
            }
            set(value: Boolean) {
                var str:String = "true"
                if(!value) str = "false"
                PropertiesComponent.getInstance()!!.setValue(addWhiteSpaceName, str)
            }
        var braceEnabled: Boolean
            get(){
                return PropertiesComponent.getInstance()!!.getBoolean(braceEnabledName, false)
            }
            set(value: Boolean) {
                var str:String = "true"
                if(!value) str = "false"
                PropertiesComponent.getInstance()!!.setValue(braceEnabledName, str)
            }
        var leftBraceCharacter: Char
            get() {
                return PropertiesComponent.getInstance()!!.getValue(leftBraceCharacterName, "{").get(0)
            }
            set(value: Char) {
                PropertiesComponent.getInstance()!!.setValue(leftBraceCharacterName, value.toString())
            }
        var rightBraceCharacter: Char
            get() {
                return PropertiesComponent.getInstance()!!.getValue(rightBraceCharacterName, "}").get(0)
            }
            set(value: Char) {
                PropertiesComponent.getInstance()!!.setValue(rightBraceCharacterName, value.toString())
            }
        var braceCharactersSettings: String
            get() {
                return PropertiesComponent.getInstance()!!.getValue(braceCharactersSettingsName, "{{ foo }}")
            }
            set(value: String) {
                var left:Char = ' '
                var right:Char = ' '
                if (value == "{{ foo }}") {
                    left = '{'
                    right = '}'
                }
                else if (value == "[[ foo ]]") {
                    left = '['
                    right = ']'
                }
                assert(left != ' ')
                assert(right != ' ')
                AngularJSConfig//.setLeft FIXME
                AngularJSConfig//.setRight FIXME

                PropertiesComponent.getInstance()!!.setValue(braceCharactersSettingsName, value)
            }
    }
}