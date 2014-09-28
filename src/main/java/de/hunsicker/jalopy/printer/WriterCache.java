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
package de.hunsicker.jalopy.printer;

import java.util.ArrayList;
import java.util.List;

import de.hunsicker.jalopy.language.CompositeFactory;
import de.hunsicker.jalopy.language.antlr.JavaNodeFactory;


/**
 * A simple cache to avoid continually creating and destroying new TestNodeWriter
 * objects.
 *
 * @since 1.0b9
 */
final class WriterCache
{
    //~ Instance variables ---------------------------------------------------------------

    /** The cached writers. */
    private final List _writers = new ArrayList();
    private final String _originalLineSeparator;

    //~ Constructors ---------------------------------------------------------------------
    NodeWriter nodeWriter = null;
    CompositeFactory _factory = null;
    /**
     * Creates a new WriterCache object.
     *
     * @param writer DOCUMENT ME!
     */
    public WriterCache(CompositeFactory factory, NodeWriter writer)
    {
        _factory = factory;
        _originalLineSeparator = writer.originalLineSeparator;
        nodeWriter = writer;
        TestNodeWriter tester = new TestNodeWriter(this,factory,nodeWriter);
        tester.originalLineSeparator = _originalLineSeparator;
        _writers.add(tester);
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns a TestNodeWriter object. If the cache is not empty, an element from the
     * cache will be returned; otherwise a new object will be created.
     *
     * @return a TestNodeWriter object.
     */
    public TestNodeWriter get()
    {
        synchronized (this)
        {
            if (_writers.size() > 0)
            {
                return (TestNodeWriter) _writers.remove(0);
            }
        }

        TestNodeWriter tester = new TestNodeWriter(this,_factory,nodeWriter);
        tester.originalLineSeparator = _originalLineSeparator;

        return tester;
    }


    /**
     * Releases the given writer and adds it to the cache.
     *
     * @param writer the writer object that should be cached.
     */
    public synchronized void release(TestNodeWriter writer)
    {
        writer.reset();
        _writers.add(writer);
    }
}
