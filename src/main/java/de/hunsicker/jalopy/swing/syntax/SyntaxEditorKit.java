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

/**
 * MoeSyntaxEditorKit.java - adapted from SyntaxEditorKit.java - jEdit's own editor kit
 * to add Syntax highlighting to the BlueJ programming environment. Copyright (C) 1998,
 * 1999 Slava Pestov This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package de.hunsicker.jalopy.swing.syntax;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;


/**
 * An implementation of <code>EditorKit</code> used for syntax colorizing. It implements
 * a view factory that maps elements to syntax colorizing views.
 * 
 * <p>
 * This editor kit can be plugged into text components to give them colorization
 * features. It can be used in other applications, not just jEdit. The syntax colorizing
 * package doesn't depend on any jEdit classes.
 * </p>
 *
 * @author Slava Pestov
 * @author Bruce Quig (BlueJ specific modifications)
 */
public final class SyntaxEditorKit
    extends DefaultEditorKit
    implements ViewFactory
{
    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns an instance of a view factory that can be used for creating views from
     * elements. This implementation returns the current instance, because this class
     * already implements <code>ViewFactory</code>.
     *
     * @return DOCUMENT ME!
     */
    public ViewFactory getViewFactory()
    {
        return this;
    }


    /**
     * Creates a view from an element that can be used for painting that element.
     *
     * @param elem The element
     *
     * @return a new SyntaxView for an element
     */
    public View create(Element elem)
    {
        return new SyntaxView(elem);
    }


    /**
     * Creates a new instance of the default document for this editor kit.
     *
     * @return DOCUMENT ME!
     */
    public Document createDefaultDocument()
    {
        SyntaxDocument document = new DefaultSyntaxDocument();
        document.setTokenMarker(new JavaTokenMarker());

        return document;
    }
}
