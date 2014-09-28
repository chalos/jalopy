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
/*
 * Copyright (c) 2005-2006, NotZippy. All rights reserved.
 *
 * This software is distributable under the BSD license. See the terms of the
 * BSD license in the documentation provided with this software.
 */
package de.hunsicker.jalopy.printer;

import java.io.IOException;

import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;

import antlr.collections.AST;


/**
 * This class prints out an annotation field
 * The field format may be similar to <br>
 * <pre>String outputFormBean() default "";</pre> 
 * @author <a href="http://jalopy.sf.net/contact.html">NotZippy</a>
 * @version $ $
 *
 * @since 1.5
 */
public class AnnotationFieldPrinter extends AbstractPrinter {
    /** Singleton. */
    private static final Printer INSTANCE = new AnnotationFieldPrinter();

    /**
     * Single instance usage only
     */
    protected AnnotationFieldPrinter() {
        super();
        // TODO Auto-generated constructor stub
    }
    
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
     * Prints the field annotation. 
     *
     * @param node
     * @param out
     * @throws IOException
     */
    public void print(AST node, NodeWriter out) throws IOException {
        
        // TODO Check to see if we should force or add comments
        printCommentsBefore(node,true,out);
        
        for(AST child = node.getFirstChild();child!=null;child=child.getNextSibling()) {
            PrinterFactory.create(child, out).print(child,out);
            // If the last child is a TYPE then add a space after it
            if (child.getType() == JavaTokenTypes.TYPE){
                out.print(SPACE,JavaTokenTypes.TYPE);
            }
            // If the last child was a RPAREN and the next sibling is not null
            // and not a SEMI print the word default
            else if (child.getType() == JavaTokenTypes.RPAREN) {
                if (child.getNextSibling()!=null && child.getNextSibling().getType()!=JavaTokenTypes.SEMI) {
                    out.print(SPACE_DEFAULT_SPACE,JavaTokenTypes.RPAREN);
                }
            }
        }
        
        printCommentsAfter(
                node, NodeWriter.NEWLINE_NO, NodeWriter.NEWLINE_YES, out);
        // Print the comments, if none print a new line
//        if (!printCommentsAfter(node,false,true,out) && !out.newline) {
//            out.printNewline();
//        }
    }

}
