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
package de.hunsicker.jalopy.swing;

import javax.swing.JLabel;

import de.hunsicker.util.ResourceBundleFactory;


/**
 * A label which displays its label text along with an integer count.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.4 $
 */
class CountLabel
    extends JLabel
{
    //~ Instance variables ---------------------------------------------------------------

    /** Used to construct the label text. */
    private StringBuffer _buffer = new StringBuffer(20);

    /** The current count. */
    private int _count;

    /** The index in the buffer where this counter's count number starts. */
    private int _index;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new CountLabel object and an initial count of zero.
     */
    public CountLabel()
    {
        this(
            ResourceBundleFactory.getBundle(
                "de.hunsicker.jalopy.swing.Bundle" /* NOI18N */).getString(
                "LBL_COUNT" /* NOI18N */));
    }


    /**
     * Creates a new CountLabel object with an initial count of zero.
     *
     * @param text the label text.
     */
    public CountLabel(String text)
    {
        this(text, 0);
    }


    /**
     * Creates a new CountLabel object.
     *
     * @param text the label text.
     * @param initialCount the initial value of the count.
     */
    public CountLabel(
        String text,
        int    initialCount)
    {
        super(text + ": " + initialCount);
        _count = initialCount;
        _index = text.length() + 2;
        _buffer.append(getText());
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Sets the counter to the given value.
     *
     * @param count the count to display.
     */
    public void setCount(int count)
    {
        _count = count;
        _buffer.delete(_index, _buffer.length());
        _buffer.append(count);

        super.setText(_buffer.toString());
    }


    /**
     * Sets the text
     *
     * @param text The new text
     */
    public final void setText(String text)
    {
        if (_index == 0)
        {
            super.setText(text);
        }
    }


    /**
     * Returns the current count.
     *
     * @return the current count.
     */
    public int getCount()
    {
        return _count;
    }


    /**
     * Increases the counter.
     */
    public void increase()
    {
        setCount(++_count);
    }


    /**
     * Resets the counter.
     */
    public void reset()
    {
        _count = 0;
        setCount(0);
    }
}
