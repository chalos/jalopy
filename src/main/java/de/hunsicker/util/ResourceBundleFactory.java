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
package de.hunsicker.util;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Serves as the central facility to aquire <code>ResourceBundle</code> objects.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.2 $
 */
public final class ResourceBundleFactory
{
    //~ Static variables/initializers ----------------------------------------------------

    /** The current locale. */
    private static Locale _locale = Locale.getDefault();

    //~ Methods --------------------------------------------------------------------------

    /**
     * Get the appropriate resource bundle for the given name.
     *
     * @param bundleName the family name of the resource bundle that contains the object
     *        in question.
     *
     * @return the appropriate bundle for the given name.
     *
     * @see java.util.ResourceBundle#getBundle(String)
     */
    public static ResourceBundle getBundle(String bundleName)
    {
        return ResourceBundle.getBundle(bundleName, _locale);
    }


    /**
     * Sets the current locale of the factory.
     *
     * @param locale a locale.
     */
    public static void setLocale(Locale locale)
    {
        _locale = locale;
    }


    /**
     * Returns the current locale of the factory.
     *
     * @return the current locale.
     */
    public static Locale getLocale()
    {
        return _locale;
    }
}
