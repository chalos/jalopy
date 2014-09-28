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

import antlr.collections.AST;
import de.hunsicker.jalopy.language.antlr.JavaNode;
import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;
import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;


/**
 * Common superclass for the printers that handle statement blocks.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.9 $
 */
abstract class BlockStatementPrinter
    extends AbstractPrinter
{
    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new BlockStatementPrinter object.
     */
    protected BlockStatementPrinter()
    {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void print(
        AST        node,
        NodeWriter out)
      throws IOException
    {
        // special handling if we encounter a label
        if (out.last == JavaTokenTypes.LABELED_STAT)
        {
            // if no newline will be printed after labels
            if (
                !AbstractPrinter.settings.getBoolean(
                    ConventionKeys.LINE_WRAP_AFTER_LABEL,
                    ConventionDefaults.LINE_WRAP_AFTER_LABEL))
            {
                // and we find comments before we have to issue one extra so
                // that the right amount of blank lines is printed
                if (((JavaNode) node).hasCommentsBefore())
                {
                    out.printNewline();
                }
            }
        }

        printCommentsBefore(node, out);
    }


    /**
     * Prints the expression list starting with the given node; the left parenthesis.
     *
     * @param lparen a LPAREN node.
     * @param insertBraces Flag
     * @param out stream to write to.
     *
     * @return the RPAREN node of the expression list.
     *
     * @throws IOException if an I/O error occured.
     *
     * @since 1.0b9
     */
    JavaNode printExpressionList(
        AST        lparen,
        boolean    insertBraces,
        NodeWriter out)
      throws IOException
    {
        if ((out.mode == NodeWriter.MODE_DEFAULT)
                && AbstractPrinter.settings.getBoolean(
                    ConventionKeys.LINE_WRAP_PAREN_GROUPING,
                    ConventionDefaults.LINE_WRAP_PAREN_GROUPING))
        	 {
        	// Check the parent to see if this is a conditional
        	switch(out.last) {
        	case JavaTokenTypes.LPAREN :
        		// This is a group
        		if (!out.newline) {
        			out.printNewline();
        		}
        	}
        		
        	}
        PrinterFactory.create(lparen, out).print(lparen, out);

        Marker marker = out.state.markers.add();
        TestNodeWriter tester = null;
        AST expr = lparen.getNextSibling();

        boolean wrapped = false; // was line wrapping performed?

        if (out.mode == NodeWriter.MODE_DEFAULT)
        {
            ParenthesesScope scope = new ParenthesesScope(out.state.paramLevel);

            out.state.expressionList = true;
            out.state.paramLevel++;
            out.state.parenScope.addFirst(scope);

            int lineLength =
                AbstractPrinter.settings.getInt(
                    ConventionKeys.LINE_LENGTH, ConventionDefaults.LINE_LENGTH);

            if (
                AbstractPrinter.settings.getBoolean(
                    ConventionKeys.LINE_WRAP_AFTER_LEFT_PAREN,
                    ConventionDefaults.LINE_WRAP_AFTER_LEFT_PAREN))
            {
                if (!out.newline)
                {
                    tester = out.testers.get();
                    PrinterFactory.create(expr, out).print(expr, tester);

                    if ((out.column + tester.length) > lineLength)
                    {
                        out.printNewline();
                        printIndentation(out);
                        wrapped = true;

                        if (
                            AbstractPrinter.settings.getBoolean(
                                ConventionKeys.LINE_WRAP_PARAMS_EXCEED,
                                ConventionDefaults.LINE_WRAP_PARAMS_EXCEED))
                        {
                            scope.wrap = true;
                        }
                    }

                    out.testers.release(tester);
                }
                else
                {
                    printIndentation(out);
                    wrapped = true;

                    if (
                        AbstractPrinter.settings.getBoolean(
                            ConventionKeys.LINE_WRAP_PARAMS_EXCEED,
                            ConventionDefaults.LINE_WRAP_PARAMS_EXCEED))
                    {
                        scope.wrap = true;
                    }
                }

                tester = out.testers.get();
                PrinterFactory.create(expr, out).print(expr, tester);
            }

            if (tester == null)
            {
                tester = out.testers.get();
                PrinterFactory.create(expr, out).print(expr, tester);
            }

            if (!wrapped && ((tester.length + out.column) > lineLength))
            {
                if (
                    AbstractPrinter.settings.getBoolean(
                        ConventionKeys.LINE_WRAP_PARAMS_EXCEED,
                        ConventionDefaults.LINE_WRAP_PARAMS_EXCEED))
                {
                    scope.wrap = true;
                }

                wrapped = true;
            }

            out.testers.release(tester);
        }

        // use continuation indentation within the parentheses?
        out.continuation =
            AbstractPrinter.settings.getBoolean(
                ConventionKeys.INDENT_CONTINUATION_BLOCK,
                ConventionDefaults.INDENT_CONTINUATION_BLOCK);

        PrinterFactory.create(expr, out).print(expr, out);

        out.continuation = false;
        out.state.wrap = false;

        if (
            wrapped
            && AbstractPrinter.settings.getBoolean(
                ConventionKeys.LINE_WRAP_BEFORE_RIGHT_PAREN,
                ConventionDefaults.LINE_WRAP_BEFORE_RIGHT_PAREN))
        {
            if (!out.newline)
            {
                out.printNewline();
            }

            if (
                AbstractPrinter.settings.getBoolean(
                    ConventionKeys.INDENT_DEEP, ConventionDefaults.INDENT_DEEP))
            {
                printIndentation(-1, out);
            }
            else
            {
                out.print(EMPTY_STRING, JavaTokenTypes.WS);
            }
        }

        JavaNode rparen = (JavaNode) expr.getNextSibling();

        AST body = rparen.getNextSibling();

        boolean hasBraces = (body.getType() == JavaTokenTypes.SLIST);
        boolean leftBraceNewline =
            AbstractPrinter.settings.getBoolean(
                ConventionKeys.BRACE_NEWLINE_LEFT, ConventionDefaults.BRACE_NEWLINE_LEFT);

        if (!hasBraces && insertBraces && !leftBraceNewline)
        {
            out.pendingComment = rparen.getCommentAfter();

            if (out.pendingComment != null)
            {
                rparen.setHiddenAfter(null);
            }
        }

        PrinterFactory.create(rparen, out).print(rparen, out);

        if (out.mode == NodeWriter.MODE_DEFAULT)
        {
            out.state.expressionList = false;
            out.state.paramLevel--;
            out.state.parenScope.removeFirst();
        }

        out.state.markers.remove(marker);

        return rparen;
    }
}
