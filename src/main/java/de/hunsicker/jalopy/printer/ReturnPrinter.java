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


/**
 * Printer for return statements (<code>LITERAL_return</code>).
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.5 $
 */
final class ReturnPrinter
    extends AbstractPrinter
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Singleton. */
    private static final Printer INSTANCE = new ReturnPrinter();

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new ReturnPrinter object.
     */
    protected ReturnPrinter()
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

        int offset = out.print(RETURN, JavaTokenTypes.LITERAL_return);

        trackPosition((JavaNode) node, out.line, offset, out);

        AST next = node.getFirstChild();

        printCommentsAfter(node, NodeWriter.NEWLINE_NO, NodeWriter.NEWLINE_NO, out);

        if (!out.newline && next.getType() != JavaTokenTypes.SEMI)
        {
            out.print(SPACE, JavaTokenTypes.WS);
        }

        Marker marker = out.state.markers.add();

        for (AST child = next; child != null; child = child.getNextSibling())
        {
            PrinterFactory.create(child, out).print(child, out);
        }

        out.state.markers.remove(marker);
    }
}
