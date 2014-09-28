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
package de.hunsicker.jalopy.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import de.hunsicker.util.ResourceBundleFactory;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Exposes the loggers that are used throughout the system.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.3 $
 */
public final class Loggers
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Logging category for all messages. */
    public static final Logger ALL = Logger.getLogger("de.hunsicker.jalopy");

    /** Logging category for I/O messages. */
    public static final Logger IO = Logger.getLogger("de.hunsicker.jalopy.io");

    /** Logging category for parsing messages. */
    public static final Logger PARSER =
        Logger.getLogger("de.hunsicker.jalopy.language.java");

    /** Logging category for Javadoc parsing messages. */
    public static final Logger PARSER_JAVADOC =
        Logger.getLogger("de.hunsicker.jalopy.language.javadoc");

    /** Logging category for printer messages. */
    public static final Logger PRINTER = Logger.getLogger("de.hunsicker.jalopy.printer");

    /** Logging category for Javadoc printer messages. */
    public static final Logger PRINTER_JAVADOC =
        Logger.getLogger("de.hunsicker.jalopy.printerjavadoc");

    /** Logging category for transformation messages. */
    public static final Logger TRANSFORM =
        Logger.getLogger("de.hunsicker.jalopy.transform");
    private static Map _config;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new Loggers object.
     */
    private Loggers()
    {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param locale DOCUMENT ME!
     */
    public static void setLocale(Locale locale)
    {
    }


    /**
     * Disables logging output.
     *
     * @since 1.0b8
     */
    public static synchronized void disable()
    {
        if (_config == null)
        {
            _config = new HashMap();
        }

        for (Enumeration i = LogManager.getCurrentLoggers(); i.hasMoreElements();)
        {
            Logger logger = (Logger) i.nextElement();

            if (logger != ALL)
            {
                List appenders = Collections.EMPTY_LIST;

                for (Enumeration j = logger.getAllAppenders(); j.hasMoreElements();)
                {
                    if (appenders == Collections.EMPTY_LIST)
                    {
                        appenders = new ArrayList(4);
                    }

                    appenders.add(j.nextElement());
                }

                _config.put(logger, appenders);
                logger.removeAllAppenders();
            }
        }
    }


    /**
     * Prints information about all appenders for the given logger. Purely a diagnostic
     * method.
     *
     * @param logger a logger.
     */
    public static void dumpAppenders(Logger logger)
    {
        System.out.println("Appenders for " + logger.getName() + ":");

        for (Enumeration i = logger.getAllAppenders(); i.hasMoreElements();)
        {
            System.out.println("    " + i.nextElement());
        }
    }


    /**
     * Enables logging output.
     *
     * @since 1.0b8
     */
    public static synchronized void enable()
    {
        if (_config != null)
        {
            for (Iterator i = _config.entrySet().iterator(); i.hasNext();)
            {
                Map.Entry entry = (Map.Entry) i.next();
                Logger logger = (Logger) entry.getKey();
                List appenders = (List) entry.getValue();

                for (int j = 0, size = appenders.size(); j < size; j++)
                {
                    Appender appender = (Appender) appenders.get(j);
                    logger.addAppender(appender);
                }
            }

            _config.clear();
        }
    }


    /**
     * Initializes logging for the given appender.
     *
     * @param appender appender to enable logging output for.
     */
    public static synchronized void initialize(Appender appender)
    {
        Convention settings = Convention.getInstance();
        ResourceBundle bundle =
            ResourceBundleFactory.getBundle(
                "de.hunsicker.jalopy.storage.Bundle" /* NOI18N */);
        ALL.setResourceBundle(bundle);

        /**
         * @todo either remove all current appenders or allow initializtion only one time
         */
        initialize(IO, appender);
        initialize(PARSER, appender);
        initialize(PARSER_JAVADOC, appender);
        initialize(TRANSFORM, appender);
        initialize(PRINTER, appender);
        initialize(PRINTER_JAVADOC, appender);

        IO.setLevel(
            Level.toLevel(
                settings.getInt(
                    ConventionKeys.MSG_PRIORITY_IO, ConventionDefaults.MSG_PRIORITY_IO)));
        PARSER.setLevel(
            Level.toLevel(
                settings.getInt(
                    ConventionKeys.MSG_PRIORITY_PARSER,
                    ConventionDefaults.MSG_PRIORITY_PARSER)));
        PARSER_JAVADOC.setLevel(
            Level.toLevel(
                settings.getInt(
                    ConventionKeys.MSG_PRIORITY_PARSER_JAVADOC,
                    ConventionDefaults.MSG_PRIORITY_PARSER_JAVADOC)));
        TRANSFORM.setLevel(
            Level.toLevel(
                settings.getInt(
                    ConventionKeys.MSG_PRIORITY_TRANSFORM,
                    ConventionDefaults.MSG_PRIORITY_TRANSFORM)));
        PRINTER.setLevel(
            Level.toLevel(
                settings.getInt(
                    ConventionKeys.MSG_PRIORITY_PRINTER_JAVADOC,
                    ConventionDefaults.MSG_PRIORITY_PRINTER_JAVADOC)));
        PRINTER_JAVADOC.setLevel(
            Level.toLevel(
                settings.getInt(
                    ConventionKeys.MSG_PRIORITY_PRINTER,
                    ConventionDefaults.MSG_PRIORITY_PRINTER)));
    }


    /**
     * Initializes the given logger. The logger will be initialized to display only
     * warnings or errors.
     *
     * @param logger logger to initialize.
     * @param appender appender to add to the logger.
     *
     * @see #initialize
     */
    private static void initialize(
        Logger   logger,
        Appender appender)
    {
        logger.removeAllAppenders();
        logger.addAppender(appender);
        logger.setLevel(Level.WARN);
    }
}
