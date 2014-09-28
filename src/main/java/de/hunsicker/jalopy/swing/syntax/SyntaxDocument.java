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
 * SyntaxDocument.java - Interface all colorized documents must implement
 * Copyright (C) 1999 Slava Pestov
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

import java.awt.Color;

import javax.swing.text.Document;


/**
 * The interface a document must implement to be colorizable by the
 * <code>SyntaxEditorKit</code>. It defines two methods, one that returns the
 * <code>TokenMarker</code> that will split a line into a list of tokens, and a method
 * that returns a color array that maps identification tags returned by the token marker
 * into <code>Color</code> objects. The possible token identifiers are defined as static
 * fields in the <code>Token</code> class.
 * 
 * <p></p>
 *
 * @author Slava Pestov
 * @version $Id: SyntaxDocument.java,v 1.2 2002/11/11 22:09:53 marcohu Exp $
 */
public interface SyntaxDocument
    extends Document
{
    //~ Methods --------------------------------------------------------------------------

    /**
     * Sets the color array that maps token identifiers to <code>java.awt.Color</code>
     * ojects. May throw an exception if this is not supported for this type of
     * document.
     *
     * @param colors The new color list
     */
    public void setColors(Color[] colors);


    /**
     * Returns the color array that maps token identifiers to <code>java.awt.Color</code>
     * objects. Each index in the array is a token type.
     *
     * @return DOCUMENT ME!
     */
    public Color[] getColors();


    /**
     * Sets the token marker that is to be used to split lines of this document up into
     * tokens. May throw an exception if this is not supported for this type of
     * document.
     *
     * @param tm The new token marker
     */
    public void setTokenMarker(TokenMarker tm);


    /**
     * Returns the token marker that is to be used to split lines of this document up
     * into tokens. May return null if this document is not to be colorized.
     *
     * @return DOCUMENT ME!
     */
    public TokenMarker getTokenMarker();


    /**
     * Reparses the document, by passing all lines to the token marker. This should be
     * called after the document is first loaded.
     */
    public void tokenizeLines();


    /**
     * Reparses the document, by passing the specified lines to the token marker. This
     * should be called after a large quantity of text is first inserted.
     *
     * @param start The first line to parse
     * @param len The number of lines, after the first one to parse
     */
    public void tokenizeLines(
        int start,
        int len);
}
