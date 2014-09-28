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

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import de.hunsicker.jalopy.language.antlr.Node;

import antlr.ASTFactory;
import antlr.Token;
import antlr.collections.AST;


/**
 * Central facility to create extended nodes.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.3 $
 */
public class NodeFactory
    extends ASTFactory
{
    //~ Static variables/initializers ----------------------------------------------------

    /** The empty string constant. */
    protected static final String EMPTY_STRING = "" /* NOI18N */.intern();
    private final CompositeFactory compositeFactory;
    private class NodeImpl extends Node{}
    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new NodeFactory object.
     */
    public NodeFactory(CompositeFactory compositeFactory)
    {
        this.compositeFactory = compositeFactory;
        this.theASTNodeType = "Node" /* NOI18N */;
        this.theASTNodeTypeClass = Node.class;
        
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Creates a new empty Node node.
     *
     * @return newly created Node.
     */

    /**
     * Creates a new empty JavaNode node.
     *
     * @return newly created Node.
     */
    public AST create()
    {
        Node node = (Node) compositeFactory.getCached(NodeFactory.class);
        
        if (node==null) {
            node = new NodeImpl();
            compositeFactory.addCached(NodeFactory.class,node);
        }
        
        return node;
    }

    /** Copy a single node with same Java AST objec type.
     *  Ignore the tokenType->Class mapping since you know
     *  the type of the node, t.getClass(), and doing a dup.
     *
     *  clone() is not used because we want all AST creation
     *  to go thru the factory so creation can be
     *  tracked.  Returns null if t is null.
     */
    public AST dup(AST t) {
        if ( t==null ) {
            return null;
        }
        AST dup_t = create();
        dup_t.initialize(t);
        return dup_t;
    }

    /**
     * Creates a new empty Node node.
     *
     * @param type information to setup the node with.
     *
     * @return newly created Node.
     */
    public AST create(int type)
    {
        AST t = create();
        t.initialize(type, EMPTY_STRING);

        return t;
    }


    /**
     * Creates a new empty Node node.
     *
     * @param type type information to setup the node with.
     * @param text text to setup the node with.
     *
     * @return newly created Node.
     */
    public AST create(
        int    type,
        String text)
    {
        AST t = create();
        t.initialize(type, text);

        return t;
    }


    /**
     * Creates a new empty Node node.
     *
     * @param node node to setup the new node with.
     *
     * @return newly created Node.
     */
    public AST create(AST node)
    {
        if (node == null)
        {
            return null;
        }

        AST t = create();
        t.initialize(node);

        return t;
    }


    /**
     * Creates a new empty Node node.
     *
     * @param tok token to setup the new node with.
     *
     * @return newly created Node.
     */
    public AST create(Token tok)
    {
        AST t = create();
        t.initialize(tok);

        return t;
    }
}
