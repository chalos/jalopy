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
 * Token.java - Generic token
 * Copyright (C) 1998, 1999 Slava Pestov
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package de.hunsicker.jalopy.swing.syntax;

/**
 * A linked list of tokens. Each token has three fields - a token identifier, which is a
 * byte value that can be looked up in the array returned by
 * <code>SyntaxDocument.getColors()</code> to get a color value, a length value which is
 * the length of the token in the text, and a pointer to the next token in the list.
 *
 * @author Slava Pestov
 * @version $Id: Token.java,v 1.1 2002/11/11 22:08:35 marcohu Exp $
 */
public class Token
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Normal text token id. This should be used to mark normal text. */
    public static final byte NULL = 0;

    /** Comment 1 token id. This can be used to mark a comment. */
    public static final byte COMMENT1 = 1;

    /** Comment 2 token id. This can be used to mark a comment. */
    public static final byte COMMENT2 = 2;

    /** Comment 2 token id. This can be used to mark a comment. */
    public static final byte COMMENT3 = 3;

    /**
     * Literal 1 token id. This can be used to mark a string literal (eg, C mode uses
     * this to mark "..." literals)
     */
    public static final byte LITERAL1 = 4;

    /**
     * Literal 2 token id. This can be used to mark an object literal (eg, Java mode uses
     * this to mark true, false, etc)
     */
    public static final byte LITERAL2 = 5;

    /**
     * Label token id. This can be used to mark labels (eg, C mode uses this to mark ...:
     * sequences)
     */
    public static final byte LABEL = 6;

    /**
     * Keyword 1 token id. This can be used to mark a keyword. This should be used for
     * general language constructs.
     */
    public static final byte KEYWORD1 = 7;

    /**
     * Keyword 2 token id. This can be used to mark a keyword. This should be used for
     * preprocessor commands, or variables.
     */
    public static final byte KEYWORD2 = 8;

    /**
     * Keyword 3 token id. This can be used to mark a keyword. This should be used for
     * data types.
     */
    public static final byte KEYWORD3 = 9;

    /**
     * Operator token id. This can be used to mark an operator. (eg, SQL mode marks +, -,
     * etc with this token type)
     */
    public static final byte OPERATOR = 10;

    /**
     * Invalid token id. This can be used to mark invalid or incomplete tokens, so the
     * user can easily spot syntax errors.
     */
    public static final byte INVALID = 11;

    /** The total number of defined token ids. */
    public static final byte ID_COUNT = 12;

    /** The first id that can be used for internal state in a token marker. */
    public static final byte INTERNAL_FIRST = 100;

    /** The last id that can be used for internal state in a token marker. */
    public static final byte INTERNAL_LAST = 126;

    /** The token type, that along with a length of 0 marks the end of the token list. */
    public static final byte END = 127;

    //~ Instance variables ---------------------------------------------------------------

    /** The next token in the linked list. */
    public Token next;

    /** The id of this token. */
    public byte id;

    /** The length of this token. */
    public int length;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new token.
     *
     * @param length The length of the token
     * @param id The id of the token
     */
    public Token(
        int  length,
        byte id)
    {
        this.length = length;
        this.id = id;
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns a string representation of this token.
     *
     * @return DOCUMENT ME!
     */
    public String toString()
    {
        return "[id=" + id + ",length=" + length + "]";
    }
}
