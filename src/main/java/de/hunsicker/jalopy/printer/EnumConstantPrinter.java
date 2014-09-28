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

import de.hunsicker.jalopy.language.antlr.JavaNode;
import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;
import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;

import antlr.collections.AST;


/**
 * The EnumConstant printer 
 */
public class EnumConstantPrinter extends BasicDeclarationPrinter {
    /** Singleton. */
    private static final Printer INSTANCE = new EnumConstantPrinter();

    /**
     * 
     */
    public EnumConstantPrinter() {
        super();
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
     * Prints the children. If child will exceed line length a new line is printed
     *
     * @param node The node
     * @param out The node writer
     * @throws IOException If an error occurs
     */
    public void print(AST node, NodeWriter out) throws IOException {
        TestNodeWriter tester = out.testers.get();
        boolean spaceAfterComma = AbstractPrinter.settings.getBoolean(
                    ConventionKeys.SPACE_AFTER_COMMA, ConventionDefaults.SPACE_AFTER_COMMA);
        
        tester.reset(out,false);
        printChildren(node,tester);
        int lineLength =
            AbstractPrinter.settings.getInt(
                ConventionKeys.LINE_LENGTH, ConventionDefaults.LINE_LENGTH);

        if (tester.line>1 || tester.column> lineLength) {
            out.printNewline();
        }
        out.testers.release(tester);
        
        addCommentIfNeeded((JavaNode)node, out);
        printCommentsBefore(node,false,out);
        printChildren(node,out);
        AST next = node.getNextSibling();
        if (next!=null) {
            if (next.getType()==JavaTokenTypes.ENUM_CONSTANT_DEF) {
                if (spaceAfterComma && !out.nextNewline) {
                    out.print(COMMA_SPACE,out.last);
                }
                else {
                    out.print(COMMA,out.last);
                }
            }
            else {
                    out.print(SEMI,out.last);
            }
        }
        else {
                out.print(SEMI,out.last);
        }
        printCommentsAfter(
                next, NodeWriter.NEWLINE_NO, NodeWriter.NEWLINE_YES, out);
        
    }
}
