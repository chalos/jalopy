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

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.jalopy.storage.Loggers;
import de.hunsicker.swing.util.SwingHelper;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * Settings page for the Jalopy messaging settings.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.2 $
 */
public class MessagesSettingsPage
    extends AbstractSettingsPage
{
    //~ Instance variables ---------------------------------------------------------------

    private JCheckBox _showStackTraceCheckBox;
    private JComboBox _generalComboBox;
    private JComboBox _javadocOutputComboBox;
    private JComboBox _javadocParserComboBox;
    private JComboBox _outputComboBox;
    private JComboBox _parserComboBox;
    private JComboBox _transComboBox;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new MessagesSettingsPage object.
     */
    public MessagesSettingsPage()
    {
        initialize();
    }


    /**
     * Creates a new MessagesSettingsPage.
     *
     * @param container the parent container.
     */
    MessagesSettingsPage(SettingsContainer container)
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
        updateLogger(Loggers.PARSER, (String) _parserComboBox.getSelectedItem());
        this.settings.putInt(
            ConventionKeys.MSG_PRIORITY_PARSER,
            Level.toLevel((String) _parserComboBox.getSelectedItem()).toInt());
        updateLogger(
            Loggers.PARSER_JAVADOC, (String) _javadocParserComboBox.getSelectedItem());
        this.settings.putInt(
            ConventionKeys.MSG_PRIORITY_PARSER_JAVADOC,
            Level.toLevel((String) _javadocParserComboBox.getSelectedItem()).toInt());
        updateLogger(Loggers.PRINTER, (String) _outputComboBox.getSelectedItem());
        this.settings.putInt(
            ConventionKeys.MSG_PRIORITY_PRINTER,
            Level.toLevel((String) _outputComboBox.getSelectedItem()).toInt());
        updateLogger(
            Loggers.PRINTER_JAVADOC, (String) _javadocOutputComboBox.getSelectedItem());
        this.settings.putInt(
            ConventionKeys.MSG_PRIORITY_PRINTER_JAVADOC,
            Level.toLevel((String) _javadocOutputComboBox.getSelectedItem()).toInt());
        updateLogger(Loggers.TRANSFORM, (String) _transComboBox.getSelectedItem());
        this.settings.putInt(
            ConventionKeys.MSG_PRIORITY_TRANSFORM,
            Level.toLevel((String) _transComboBox.getSelectedItem()).toInt());
        updateLogger(Loggers.IO, (String) _generalComboBox.getSelectedItem());
        this.settings.putInt(
            ConventionKeys.MSG_PRIORITY_IO,
            Level.toLevel((String) _generalComboBox.getSelectedItem()).toInt());
        this.settings.putBoolean(
            ConventionKeys.MSG_SHOW_ERROR_STACKTRACE, _showStackTraceCheckBox.isSelected());
    }


    /**
     * Initializes the UI.
     */
    private void initialize()
    {
        String[] prios =
        {
            Level.DEBUG.toString(), Level.INFO.toString(), Level.WARN.toString(),
            Level.ERROR.toString()
        };
        ComboBoxPanel parserMessages =
            new ComboBoxPanel(
                this.bundle.getString("LBL_CAT_PARSER" /* NOI18N */), prios,
                Level.toLevel(
                    this.settings.getInt(
                        ConventionKeys.MSG_PRIORITY_PARSER,
                        ConventionDefaults.MSG_PRIORITY_PARSER)).toString());
        _parserComboBox = parserMessages.getComboBox();

        ComboBoxPanel javadocParserMessages =
            new ComboBoxPanel(
                this.bundle.getString("LBL_CAT_PARSER_JAVADOC" /* NOI18N */), prios,
                Level.toLevel(
                    this.settings.getInt(
                        ConventionKeys.MSG_PRIORITY_PARSER_JAVADOC,
                        ConventionDefaults.MSG_PRIORITY_PARSER_JAVADOC)).toString());
        _javadocParserComboBox = javadocParserMessages.getComboBox();

        ComboBoxPanel transMessages =
            new ComboBoxPanel(
                this.bundle.getString("LBL_CAT_TRANSFORM" /* NOI18N */), prios,
                Level.toLevel(
                    this.settings.getInt(
                        ConventionKeys.MSG_PRIORITY_TRANSFORM,
                        ConventionDefaults.MSG_PRIORITY_TRANSFORM)).toString());
        _transComboBox = transMessages.getComboBox();

        ComboBoxPanel outputMessages =
            new ComboBoxPanel(
                this.bundle.getString("LBL_CAT_PRINTER" /* NOI18N */), prios,
                Level.toLevel(
                    this.settings.getInt(
                        ConventionKeys.MSG_PRIORITY_PRINTER,
                        ConventionDefaults.MSG_PRIORITY_PRINTER)).toString());
        _outputComboBox = outputMessages.getComboBox();

        ComboBoxPanel javadocOutputMessages =
            new ComboBoxPanel(
                this.bundle.getString("LBL_CAT_PRINTER_JAVADOC" /* NOI18N */), prios,
                Level.toLevel(
                    this.settings.getInt(
                        ConventionKeys.MSG_PRIORITY_PRINTER_JAVADOC,
                        ConventionDefaults.MSG_PRIORITY_PRINTER_JAVADOC)).toString());
        _javadocOutputComboBox = javadocOutputMessages.getComboBox();

        ComboBoxPanel generalMessages =
            new ComboBoxPanel(
                this.bundle.getString("LBL_CAT_GENERAL" /* NOI18N */), prios,
                Level.toLevel(
                    this.settings.getInt(
                        ConventionKeys.MSG_PRIORITY_IO, ConventionDefaults.MSG_PRIORITY_IO))
                     .toString());
        _generalComboBox = generalMessages.getComboBox();

        JPanel categories = new JPanel();
        categories.setLayout(new BoxLayout(categories, BoxLayout.Y_AXIS));
        categories.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    this.bundle.getString("BDR_CATEGORIES" /* NOI18N */)),
                BorderFactory.createEmptyBorder(0, 5, 5, 5)));
        categories.add(generalMessages);
        categories.add(parserMessages);
        categories.add(javadocParserMessages);
        categories.add(transMessages);
        categories.add(outputMessages);
        categories.add(javadocOutputMessages);
        _showStackTraceCheckBox =
            new JCheckBox(
                this.bundle.getString("CHK_SHOW_STACKTRACE" /* NOI18N */),
                this.settings.getBoolean(
                    ConventionKeys.MSG_SHOW_ERROR_STACKTRACE,
                    ConventionDefaults.MSG_SHOW_ERROR_STACKTRACE));

        JPanel misc = new JPanel();
        misc.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        misc.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    this.bundle.getString("BDR_MISC" /* NOI18N */)),
                BorderFactory.createEmptyBorder(0, 5, 5, 5)));
        misc.add(_showStackTraceCheckBox);
        misc.setAlignmentY(Component.TOP_ALIGNMENT);

        GridBagLayout gridBag = new GridBagLayout();
        setLayout(gridBag);

        GridBagConstraints c = new GridBagConstraints();

        c.insets.top = 10;
        c.insets.bottom = 10;
        SwingHelper.setConstraints(
            c, 0, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        gridBag.setConstraints(categories, c);
        add(categories);

        c.insets.top = 0;
        SwingHelper.setConstraints(
            c, 0, 1, GridBagConstraints.REMAINDER, 1, 1.0, 1.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, c.insets, 0, 0);
        gridBag.setConstraints(misc, c);
        add(misc);
    }


    /**
     * Updates the given logger.
     *
     * @param logger the logger.
     * @param level level to assign to the logger.
     *
     * @see de.hunsicker.hunsicker.plugin.AbstractPlugin#getAppender
     */
    private void updateLogger(
        Logger logger,
        String level)
    {
        logger.setLevel(Level.toLevel(level));
    }
}
