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
 * Printer for try/catch/finally constructs [<code>LITERAL_try</code>,
 * <code>LITERAL_catch</code>, <code>LITERAL_finally</code>].
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.6 $
 */
final class TryCatchFinallyPrinter
    extends AbstractPrinter
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Singleton. */
    private static final Printer INSTANCE = new TryCatchFinallyPrinter();

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new TryCatchFinallyPrinter object.
     */
    protected TryCatchFinallyPrinter()
    {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns the sole instance of this class.
     *
     * @return the sole instance of this class.
     */
    public static final Printer getInstance()
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
        out.print(TRY, JavaTokenTypes.LITERAL_try);
        printCommentsAfter(node, NodeWriter.NEWLINE_NO, NodeWriter.NEWLINE_NO, out);

        for (AST child = node.getFirstChild(); child != null;
            child = child.getNextSibling())
        {
            switch (child.getType())
            {
                case JavaTokenTypes.SLIST :
                    PrinterFactory.create(child, out).print(child, out);

                    break;

                case JavaTokenTypes.LITERAL_catch :
                    printCatch(child, out);

                    break;

                case JavaTokenTypes.LITERAL_finally :
                    printFinallyPart(child, out);

                    break;

                default :
                    throw new IllegalArgumentException("illegal type -- " + child);
            }
        }

        out.last = JavaTokenTypes.RCURLY;
    }

    /**
     * Prints the catch part of the catch clause.
     *
     * @param node the LITERAL_finally node of the catch clause.
     * @param out stream to write to.
     *
     * @throws IOException if an I/O error occured.
     */
    private void printCatch(
        AST        node,
        NodeWriter out)
      throws IOException
    {
        printCommentsBefore(node, out);
        logIssues(node, out);

        // if no comment was printed before us, print extra indentation
        if (!out.newline)
        {
            out.print(
                out.getString(
                    AbstractPrinter.settings.getInt(
                        ConventionKeys.INDENT_SIZE_BRACE_RIGHT_AFTER,
                        ConventionDefaults.INDENT_SIZE_BRACE_RIGHT_AFTER)),
                JavaTokenTypes.WS);
        }

        out.print(CATCH, JavaTokenTypes.LITERAL_catch);

        if (
            AbstractPrinter.settings.getBoolean(
                ConventionKeys.SPACE_BEFORE_STATEMENT_PAREN,
                ConventionDefaults.SPACE_BEFORE_STATEMENT_PAREN))
        {
            out.print(SPACE, JavaTokenTypes.LITERAL_catch);
        }

        AST lparen = node.getFirstChild();
        PrinterFactory.create(lparen, out).print(lparen, out);

        AST parameters = lparen.getNextSibling();
        PrinterFactory.create(parameters, out).print(parameters, out);

        AST rparen = parameters.getNextSibling();
        PrinterFactory.create(rparen, out).print(rparen, out);

        JavaNode body = (JavaNode) rparen.getNextSibling();
        PrinterFactory.create(body, out).print(body, out);
    }


    /**
     * Prints the finally part of the catch clause.
     *
     * @param node the LITERAL_finally node of the catch clause.
     * @param out stream to write to.
     *
     * @throws IOException if an I/O error occured.
     */
    private void printFinallyPart(
        AST        node,
        NodeWriter out)
      throws IOException
    {
        printCommentsBefore(node, out);
        logIssues(node, out);

        // if no comment was printed before us, print extra indentation
        if (!out.newline)
        {
            out.print(
                out.getString(
                    AbstractPrinter.settings.getInt(
                        ConventionKeys.INDENT_SIZE_BRACE_RIGHT_AFTER,
                        ConventionDefaults.INDENT_SIZE_BRACE_RIGHT_AFTER)),
                JavaTokenTypes.WS);
        }

        out.print(FINALLY, JavaTokenTypes.LITERAL_finally);
        printCommentsAfter(node, out);

        JavaNode body = (JavaNode) node.getFirstChild();
        PrinterFactory.create(body, out).print(body, out);
    }
}
