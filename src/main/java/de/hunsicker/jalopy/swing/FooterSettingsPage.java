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

import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;


/**
 * Settings page for the Jalopy printer footer settings.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.3 $
 */
public class FooterSettingsPage
    extends HeaderSettingsPage
{
    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new FooterSettingsPage object.
     */
    public FooterSettingsPage()
    {
        super();
    }


    /**
     * Creates a new FooterSettingsPage.
     *
     * @param container the parent container.
     */
    FooterSettingsPage(SettingsContainer container)
    {
        super(container);
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    protected Convention.Key getBlankLinesAfterKey()
    {
        return ConventionKeys.BLANK_LINES_AFTER_FOOTER;
    }


    /**
     * {@inheritDoc}
     */
    protected Convention.Key getBlankLinesBeforeKey()
    {
        return ConventionKeys.BLANK_LINES_BEFORE_FOOTER;
    }


    /**
     * {@inheritDoc}
     */
    protected Convention.Key getConventionKeysKey()
    {
        return ConventionKeys.FOOTER_KEYS;
    }


    /**
     * {@inheritDoc}
     */
    protected String getDefaultAfter()
    {
        return String.valueOf(ConventionDefaults.BLANK_LINES_AFTER_FOOTER);
    }


    /**
     * {@inheritDoc}
     */
    protected String getDeleteLabel()
    {
        return this.bundle.getString("BDR_DELETE_FOOTERS" /* NOI18N */);
    }


    /**
     * {@inheritDoc}
     */
    protected String[] getItemsAfter()
    {
        return createItemList(new int[] { 1, 2, 3, 4, 5 });
    }


    /**
     * {@inheritDoc}
     */
    protected Convention.Key getSmartModeKey()
    {
        return ConventionKeys.FOOTER_SMART_MODE_LINES;
    }


    /**
     * {@inheritDoc}
     */
    protected Convention.Key getTextKey()
    {
        return ConventionKeys.FOOTER_TEXT;
    }


    /**
     * {@inheritDoc}
     */
    protected Convention.Key getUseKey()
    {
        return ConventionKeys.FOOTER;
    }

    
    /**
     * {@inheritDoc}
     */
    protected Convention.Key getIgnoreIfExistsKey()
    {
        return ConventionKeys.FOOTER_IGNORE_IF_EXISTS;
    }


    /**
     * {@inheritDoc}
     */
    protected String getUseLabel()
    {
        return this.bundle.getString("CHK_USE_FOOTER" /* NOI18N */);
    }

    
    /**
     * {@inheritDoc}
     */
    protected String getIgnoreIfExistsLabel()
    {
        return this.bundle.getString("CHK_IGNORE_FOOTER_IF_EXISTS" /* NOI18N */);
    }
}
