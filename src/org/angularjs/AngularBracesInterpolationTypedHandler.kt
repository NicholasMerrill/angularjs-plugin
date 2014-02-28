package org.angularjs

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.ide.highlighter.HtmlFileType
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * Heavily borrow from @dcheryasov's work on Django braces
 */
public open class AngularBracesInterpolationTypedHandler(): TypedHandlerDelegate() {
    public override fun beforeCharTyped(c: Char, project: Project?, editor: Editor?, file: PsiFile?, fileType: FileType?): TypedHandlerDelegate.Result? {
        if(!AngularJSConfig.braceEnabled) return TypedHandlerDelegate.Result.DEFAULT;
        if (file?.getFileType() == HtmlFileType.INSTANCE)
        {
            val leftBraceChar = AngularJSConfig.leftBraceCharacter
            val rightBraceChar = AngularJSConfig.rightBraceCharacter
//            var rightBraceChar = ' '
//            when (leftBraceChar) {
//                '{' -> rightBraceChar = '}'
//                '[' -> rightBraceChar = ']'
//            }
//            assert(rightBraceChar != ' ');
            if (c == leftBraceChar)
            {
                val addWhiteSpaceBetweenBraces = AngularJSConfig.whiteSpace
                val document: Document? = editor?.getDocument()
                val offset: Int = editor?.getCaretModel()?.getOffset()!!
                var chars: CharSequence? = document?.getCharsSequence()
                if (offset > 0 && (chars?.charAt(offset - 1)) == leftBraceChar)
                {
                    if (offset < 2 || (chars?.charAt(offset - 2)) != leftBraceChar)
                    {
                        if (alreadyHasEnding(chars, c, offset))
                        {
                            return TypedHandlerDelegate.Result.CONTINUE
                        }
                        else
                        {
                            var interpolation: String? = null
                            if (c == leftBraceChar)
                            {
                                if(addWhiteSpaceBetweenBraces)
                                {
                                    interpolation = "${leftBraceChar} ${rightBraceChar}"
                                }
                                else{
                                    interpolation = "${leftBraceChar}${rightBraceChar}"
                                }
                            }

                            if (interpolation != null)
                            {
                                if (offset == (chars?.length()) || (offset < (chars?.length())!! && (chars?.charAt(offset)) != '}'))
                                {
                                    interpolation += rightBraceChar
                                }

                                var move = 2
                                if(!addWhiteSpaceBetweenBraces) move = 1

                                typeInStringAndMoveCaret(editor, offset + move, interpolation)
                                return TypedHandlerDelegate.Result.STOP
                            }

                        }
                    }

                }

            }

        }

        return TypedHandlerDelegate.Result.CONTINUE
    }

    class object {
        open fun typeInStringAndMoveCaret(editor: Editor?, offset: Int, str: String?): Unit {
            EditorModificationUtil.typeInStringAtCaretHonorBlockSelection(editor, str, true)
            editor?.getCaretModel()?.moveToOffset(offset)
        }
        private open fun alreadyHasEnding(chars: CharSequence?, c: Char, offset: Int): Boolean {
            var i: Int = offset
            var endChar: Char
            val leftBraceChar = AngularJSConfig.leftBraceCharacter
            val rightBraceChar = AngularJSConfig.rightBraceCharacter
            if (c == leftBraceChar)
            {
                endChar = rightBraceChar
            }
            else
            {
                endChar = c
            }
            while (i < (chars?.length())!! && ((chars?.charAt(i)) != leftBraceChar && (chars?.charAt(i)) != endChar && (chars?.charAt(i)) != '\n'))
            {
                i++
            }
            if (i + 1 < (chars?.length())!! && (chars?.charAt(i)) == endChar && (chars?.charAt(i + 1)) == rightBraceChar)
            {
                return true
            }

            return false
        }
    }
}
