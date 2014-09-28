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

import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;

import antlr.collections.AST;


/**
 * TODO 
 */
public class TypeArgumentsPrinter extends AbstractPrinter {
    /** Singleton. */
    private static final Printer INSTANCE = new TypeArgumentsPrinter();

    /**
     * 
     */
    public TypeArgumentsPrinter() {
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
     * TODO 
     *
     * @param node
     * @param out
     * @throws IOException
     */
    public void print(AST node, NodeWriter out) throws IOException {
        out.print("<", node.getType());
        boolean first = true;
        // TODO validate this is ok for all 
        for(AST child = node.getFirstChild();child!=null;child = child.getNextSibling()) {
            processTypeArguement(child,out,first);
            first=false;
        }
        out.print(">", node.getType());

    }

    /**
     * @param child
     * @param out
     * @throws IOException
     */
    private void processTypeArguement(AST node, NodeWriter out,boolean first) throws IOException {
        // TODO Clean up commas
    	if (node.getType() == JavaTokenTypes.COMMA) {
        	    PrinterFactory.create(node, out).print(node,out);
    	}
    	else
        for(AST child = node.getFirstChild();child!=null;child = child.getNextSibling()) {
            switch(child.getType()) {
                case JavaTokenTypes.WILDCARD_TYPE:
                    out.print(QUESTION,child.getType());
                	processTypeArguement(child,out,true);
                	break;
                case JavaTokenTypes.TYPE_UPPER_BOUNDS:
                    out.print(EXTENDS_SPACE,child.getType());
	            	processTypeArguement(child,out,true);
            	break;
                case JavaTokenTypes.TYPE_LOWER_BOUNDS:
                    out.print(SUPER_SPACE,child.getType());
                	processTypeArguement(child,out,true);
            	break;
            	
            	default :
            	    PrinterFactory.create(child, out).print(child,out);
            }
        }
    }
}
