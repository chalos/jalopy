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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.swing.util.SwingHelper;


/**
 * Settings page for the Jalopy printer comment settings.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.2 $
 */
public class EnumSettingsPage
    extends AbstractSettingsPage
{
    //~ Instance variables ---------------------------------------------------------------

    private JCheckBox _formatMultiLineCheckBox;
    private JCheckBox _removeJavadocCheckBox;
    private JCheckBox _removeMultiCheckBox;
    private JCheckBox _removeSingleCheckBox;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new CommentsSettingsPage object.
     */
    public EnumSettingsPage()
    {
        initialize();
    }


    /**
     * Creates a new CommentsSettingsPage.
     *
     * @param container the parent container.
     */
    EnumSettingsPage(SettingsContainer container)
    {
        super(container);
        initialize();
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void updateSettings()
    {
        this.settings.putBoolean(
            ConventionKeys.COMMENT_REMOVE_SINGLE_LINE, _removeSingleCheckBox.isSelected());
        this.settings.putBoolean(
            ConventionKeys.COMMENT_REMOVE_MULTI_LINE, _removeMultiCheckBox.isSelected());
        this.settings.putBoolean(
            ConventionKeys.COMMENT_JAVADOC_REMOVE, _removeJavadocCheckBox.isSelected());
        this.settings.putBoolean(
            ConventionKeys.COMMENT_FORMAT_MULTI_LINE,
            _formatMultiLineCheckBox.isSelected());
    }


    private void initialize()
    {
        JPanel removePanel = new JPanel();
        GridBagLayout removeLayout = new GridBagLayout();
        removePanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    this.bundle.getString("BDR_REMOVE" /* NOI18N */)),
                BorderFactory.createEmptyBorder(0, 5, 5, 0)));
        removePanel.setLayout(removeLayout);

        GridBagConstraints c = new GridBagConstraints();

        _removeSingleCheckBox =
            new JCheckBox(
                this.bundle.getString("CHK_SINGLE_LINE" /* NOI18N */),
                this.settings.getBoolean(
                    ConventionKeys.COMMENT_REMOVE_SINGLE_LINE,
                    ConventionDefaults.COMMENT_REMOVE_SINGLE_LINE));
        _removeSingleCheckBox.addActionListener(this.trigger);
        SwingHelper.setConstraints(
            c, 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        removeLayout.setConstraints(_removeSingleCheckBox, c);
        removePanel.add(_removeSingleCheckBox);

        _removeMultiCheckBox =
            new JCheckBox(
                this.bundle.getString("CHK_MULTI_LINE" /* NOI18N */),
                this.settings.getBoolean(
                    ConventionKeys.COMMENT_REMOVE_MULTI_LINE,
                    ConventionDefaults.COMMENT_REMOVE_MULTI_LINE));
        _removeMultiCheckBox.addActionListener(this.trigger);
        SwingHelper.setConstraints(
            c, 1, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        removeLayout.setConstraints(_removeMultiCheckBox, c);
        removePanel.add(_removeMultiCheckBox);

        _removeJavadocCheckBox =
            new JCheckBox(
                this.bundle.getString("CHK_JAVADOC" /* NOI18N */),
                this.settings.getBoolean(
                    ConventionKeys.COMMENT_JAVADOC_REMOVE,
                    ConventionDefaults.COMMENT_JAVADOC_REMOVE));
        _removeJavadocCheckBox.addActionListener(this.trigger);
        SwingHelper.setConstraints(
            c, 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        removeLayout.setConstraints(_removeJavadocCheckBox, c);
        removePanel.add(_removeJavadocCheckBox);

        JPanel formatPanel = new JPanel();
        GridBagLayout formatPanelLayout = new GridBagLayout();
        formatPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    this.bundle.getString("BDR_FORMAT" /* NOI18N */)),
                BorderFactory.createEmptyBorder(0, 5, 5, 0)));
        formatPanel.setLayout(formatPanelLayout);

        _formatMultiLineCheckBox =
            new JCheckBox(
                this.bundle.getString("CHK_MULTI_LINE" /* NOI18N */),
                this.settings.getBoolean(
                    ConventionKeys.COMMENT_FORMAT_MULTI_LINE,
                    ConventionDefaults.COMMENT_FORMAT_MULTI_LINE));
        _formatMultiLineCheckBox.addActionListener(this.trigger);
        SwingHelper.setConstraints(
            c, 0, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        formatPanelLayout.setConstraints(_formatMultiLineCheckBox, c);
        formatPanel.add(_formatMultiLineCheckBox);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        c.insets.top = 10;
        SwingHelper.setConstraints(
            c, 0, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        layout.setConstraints(removePanel, c);
        add(removePanel);

        c.insets.bottom = 10;
        SwingHelper.setConstraints(
            c, 0, 1, GridBagConstraints.REMAINDER, 1, 1.0, 1.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        layout.setConstraints(formatPanel, c);
        add(formatPanel);
    }
}
