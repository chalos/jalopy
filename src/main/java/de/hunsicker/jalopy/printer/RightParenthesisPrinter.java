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
import java.util.List;

import antlr.collections.AST;
import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;
import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;


/**
 * Printer for the right parenthesis [<code>RPAREN</code>].
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.5 $
 *
 * @since 1.0b9
 */
final class RightParenthesisPrinter
    extends AbstractPrinter
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Singleton. */
    private static final RightParenthesisPrinter INSTANCE = new RightParenthesisPrinter();

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new RightParenthesisPrinter object.
     */
    private RightParenthesisPrinter()
    {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns the sole instance of this class.
     *
     * @return the sole instance of this class.
     */
    public static RightParenthesisPrinter getInstance()
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
        if (
            (out.mode == NodeWriter.MODE_DEFAULT)
            && AbstractPrinter.settings.getBoolean(
                ConventionKeys.LINE_WRAP_PAREN_GROUPING,
                ConventionDefaults.LINE_WRAP_PAREN_GROUPING))
        {
            List parentheses = out.state.parentheses;

            for (int i = 0, size = parentheses.size(); i < size; i++)
            {
                Object parenthesis = parentheses.get(i);

                if (parenthesis == node)
                {
                    out.printNewline();

                    if (
                        AbstractPrinter.settings.getBoolean(
                            ConventionKeys.INDENT_DEEP, ConventionDefaults.INDENT_DEEP))
                    {
                        out.state.markers.remove(out.state.markers.getLast());
                    }
                    else
                    {
                        out.unindent();
                    }

                    printIndentation(out);
                    parentheses.remove(i);

                    break;
                }
            }
        }

        printCommentsBefore(node, NodeWriter.NEWLINE_NO, out);

        if (
            AbstractPrinter.settings.getBoolean(
                ConventionKeys.PADDING_PAREN, ConventionDefaults.PADDING_PAREN))
        {
            out.print(SPACE_RPAREN, JavaTokenTypes.RPAREN);
        }
        else
        {
            out.print(RPAREN, JavaTokenTypes.RPAREN);
        }

        printCommentsAfter(node, NodeWriter.NEWLINE_NO, NodeWriter.NEWLINE_NO, out);
    }
}
