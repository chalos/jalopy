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

import java.awt.Color;
import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


/**
 * A component that can be used to display/edit the Jalopy brace settings for the if-else
 * handling.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.3 $
 */
class BracesIfElsePanel
    extends AbstractSettingsPage
{
    //~ Static variables/initializers ----------------------------------------------------

    static ResourceBundle res =
        ResourceBundle.getBundle("de.hunsicker.jalopy.swing.Bundle" /* NOI18N */);

    //~ Instance variables ---------------------------------------------------------------

    private JCheckBox _singleElseCheckBox;
    private JCheckBox _singleIfCheckBox;
    private JCheckBox _specialIfElseCheckBox;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new BracesIfElsePanel.
     */
    public BracesIfElsePanel()
    {
        initialize();
    }


    /**
     * Creates a new BracesIfElsePanel.
     *
     * @param container the parent container.
     */
    BracesIfElsePanel(SettingsContainer container)
    {
        super(container);
        initialize();
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Empty
     */
    public void updateSettings()
    {
    }


    /**
     * Initializes the UI.
     */
    private void initialize()
    {
        JPanel ifElsePanel = new JPanel();
        ifElsePanel.setBorder(
            BorderFactory.createCompoundBorder(
                new TitledBorder(
                    BorderFactory.createLineBorder(new Color(153, 153, 153), 1),
                    " if-else "), BorderFactory.createEmptyBorder(0, 5, 5, 5)));
        ifElsePanel.setLayout(new BoxLayout(ifElsePanel, BoxLayout.Y_AXIS));
        ifElsePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        _singleIfCheckBox = new JCheckBox("Single if-statement in same line");
        ifElsePanel.add(_singleIfCheckBox);
        _singleElseCheckBox = new JCheckBox("Single else-statement in same line");
        ifElsePanel.add(_singleElseCheckBox);
        _specialIfElseCheckBox = new JCheckBox("Special if-else treatment");
        ifElsePanel.add(_specialIfElseCheckBox);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(10));
        add(ifElsePanel);
    }
}
