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
 * Printer for creator constructs [<code>LITERAL_new</code>].
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.4 $
 */
final class CreatorPrinter
    extends AbstractPrinter
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Singleton. */
    private static final Printer INSTANCE = new CreatorPrinter();

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new CreatorPrinter object.
     */
    private CreatorPrinter()
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

        out.print(NEW_SPACE, JavaTokenTypes.LITERAL_new);

        for (AST child = node.getFirstChild(); child != null;
            child = child.getNextSibling())
        {
            switch (child.getType())
            {
                case JavaTokenTypes.OBJBLOCK :
                    out.state.anonymousInnerClass = true;

                    JavaNode n = (JavaNode) node;

                    switch (n.getParent().getParent().getType())
                    {
                        // if the creator starts an anonymous inner class and
                        // is part of another creator or method call, we have
                        // to adjust the parentheses nesting level because the
                        // indentation printing depends on this setting and
                        // would indent one level to much if left alone
                        case JavaTokenTypes.ELIST :
                            out.indent();
                            out.state.paramLevel--;
                            PrinterFactory.create(child, out).print(child, out);
                            out.unindent();
                            out.state.paramLevel++;

                            break;

                        case JavaTokenTypes.ASSIGN :
                            PrinterFactory.create(child, out).print(child, out);

                            break;

                        default :
                            out.indent();
                            PrinterFactory.create(child, out).print(child, out);
                            out.unindent();

                            break;
                    }

                    out.state.anonymousInnerClass = false;

                    // hint for correct blank lines behaviour
                    out.last = JavaTokenTypes.CLASS_DEF;

                    break;

                default :
                    PrinterFactory.create(child, out).print(child, out);

                    break;
            }
        }
    }
}
