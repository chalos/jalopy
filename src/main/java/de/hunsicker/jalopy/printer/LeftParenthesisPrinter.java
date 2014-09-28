/*
 * This file is part of Jalopy.
 *
 * Copyright (c) 2001-2004, Marco Hunsicker. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of the Jalopy Group nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.hunsicker.jalopy.printer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import antlr.collections.AST;
import de.hunsicker.jalopy.language.antlr.JavaNode;
import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;
import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;


/**
 * Printer for the left parenthesis [<code>LPAREN</code>].
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.6 $
 *
 * @since 1.0b9
 */
final class LeftParenthesisPrinter
    extends AbstractPrinter
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Singleton. */
    private static final LeftParenthesisPrinter INSTANCE = new LeftParenthesisPrinter();

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new LeftParenthesisPrinter object.
     */
    private LeftParenthesisPrinter()
    {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns the sole instance of this class.
     *
     * @return the sole instance of this class.
     */
    public static LeftParenthesisPrinter getInstance()
    {
        return INSTANCE;
    }
    /**
     * {@inheritDoc}
     */
    public void print(
        AST        node,
        NodeWriter out)
      throws IOException
    {
        printCommentsBefore(node, out);

        if (
            AbstractPrinter.settings.getBoolean(
                ConventionKeys.PADDING_PAREN, ConventionDefaults.PADDING_PAREN))
        {
            out.print(LPAREN_SPACE, JavaTokenTypes.LPAREN);
        }
        else
        {
            out.print(LPAREN, JavaTokenTypes.LPAREN);
        }

        printCommentsAfter(node, NodeWriter.NEWLINE_NO, NodeWriter.NEWLINE_YES, out);

        if (
            (out.mode == NodeWriter.MODE_DEFAULT)
            && AbstractPrinter.settings.getBoolean(
                ConventionKeys.LINE_WRAP_PAREN_GROUPING,
                ConventionDefaults.LINE_WRAP_PAREN_GROUPING))
        {
            switch (node.getType())
            {
                case JavaTokenTypes.LPAREN :

                    JavaNode parent = ((JavaNode) node).getParent();

                    switch (parent.getType())
                    {
                        case JavaTokenTypes.METHOD_DEF :
                        //case JavaTokenTypes.EXPR :
                        case JavaTokenTypes.CTOR_DEF :
                        case JavaTokenTypes.DOT :
                        
                        case JavaTokenTypes.LITERAL_while :
                        case JavaTokenTypes.LITERAL_do :
                        case JavaTokenTypes.LITERAL_new :
                        case JavaTokenTypes.LITERAL_switch :
                        case JavaTokenTypes.LITERAL_catch :
                        case JavaTokenTypes.LITERAL_for :
                        case JavaTokenTypes.METHOD_CALL :
                        case JavaTokenTypes.LITERAL_if :
                            break;
                        default :
                            wrap(node, parent, out);

                            break;
                    }

                    break;
            }
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @param leftParen DOCUMENT ME!
     * @param parent DOCUMENT ME!
     * @param out DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     *
     * @since 1.0b9
     */
    private void wrap(
        AST        leftParen,
        JavaNode   parent,
        NodeWriter out)
      throws IOException
    {
        TestNodeWriter tester = out.testers.get();

        AST rightParen = null;
        int count = 0;

ITERATION:
        for (AST child = leftParen; child != null; child = child.getNextSibling())
        {
            PrinterFactory.create(child, out).print(child, tester);

            switch (child.getType())
            {
                case JavaTokenTypes.LPAREN :
                    count++;

                    break;

                case JavaTokenTypes.RPAREN :
                    count--;

                    if (count == 0)
                    {
                        rightParen = child;

                        break ITERATION;
                    }

                    break;
            }
        }

        int lineLength =
            AbstractPrinter.settings.getInt(
                ConventionKeys.LINE_LENGTH, ConventionDefaults.LINE_LENGTH);

        if ((out.column + tester.length) > lineLength)
        {
            out.printNewline();

            if (
                AbstractPrinter.settings.getBoolean(
                    ConventionKeys.INDENT_DEEP, ConventionDefaults.INDENT_DEEP))
            {
                out.state.markers.add(
                    out.line, out.state.markers.getLast().column + out.indentSize);
            }
            else
            {
                out.indent();
            }

            out.state.parentheses.add(rightParen);

            printIndentation(out);
        }

        out.testers.release(tester);
    }
}
