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
package de.hunsicker.jalopy.language;


/**
 * A class that wraps some application specific annotation data. It can be used to track
 * the position information for things like debugger breakpoints, erroneous lines, and
 * so on.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.3 $
 *
 * @see de.hunsicker.jalopy.language.JavaRecognizer#attachAnnotations
 * @since 1.0b9
 */
public final class Annotation
{
    //~ Instance variables ---------------------------------------------------------------

    /** The application specific annotation data. */
    private final Object _data;

    /** The line number where the annotation belongs to. */
    private int _line;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new Annotation object.
     *
     * @param line the line number to which the annotation is attached.
     * @param data the application specific annotation data.
     */
    public Annotation(
        int    line,
        Object data)
    {
        _line = line;
        _data = data;
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns the application specific annotation data.
     *
     * @return annotation data.
     */
    public Object getData()
    {
        return _data;
    }


    /**
     * Sets the line number where this annotation belongs.
     *
     * @param line line number (<code>&gt;= 1</code>).
     *
     * @throws IllegalArgumentException if <code><em>line</em> &lt; 1</code>
     */
    public void setLine(int line)
    {
        if (line < 1)
        {
            throw new IllegalArgumentException();
        }

        _line = line;
    }


    /**
     * Returns the (1-based) line number where this annotation belongs.
     *
     * @return the line number where this annotation belongs (<code>&gt;= 1</code>).
     */
    public int getLine()
    {
        return _line;
    }
}
