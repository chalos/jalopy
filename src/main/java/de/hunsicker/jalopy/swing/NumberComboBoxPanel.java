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

import javax.swing.JComboBox;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.3 $
 */
public class NumberComboBoxPanel
    extends ComboBoxPanel
{
    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new NumberComboBoxPanel object.
     *
     * @param text the label text.
     * @param items the items to display in the checkbox.
     */
    public NumberComboBoxPanel(
        String   text,
        Object[] items)
    {
        super(text.trim(), items);
        initialize();
    }


    /**
     * Creates a new NumberComboBoxPanel object.
     *
     * @param text the label text.
     * @param items the items to display in the checkbox.
     * @param item the item to be initially selected.
     */
    public NumberComboBoxPanel(
        String   text,
        Object[] items,
        Object   item)
    {
        super(text.trim(), items, item);
        initialize();
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void initialize()
    {
        JComboBox combo = getComboBox();
        combo.setEditor(
            new NumberComboBoxEditor(
                Integer.parseInt((String) combo.getSelectedItem()), 3));
        combo.setEditable(true);
    }
}
