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

import antlr.ASTFactory;
import antlr.RecognitionException;
import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.collections.AST;


/**
 * Common interface for ANTLR parsers.
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.4 $
 */
public interface Parser
{
    //~ Methods --------------------------------------------------------------------------

    /**
     * Sets the factory used to create tree nodes.
     *
     * @param factory factory to use.
     */
    public void setASTFactory(ASTFactory factory);


    /**
     * Sets the factory used to create the nodes of the parse tree.
     *
     * @return the used node factory.
     */
    public ASTFactory getASTFactory();


    /**
     * Sets the filename we parse.
     *
     * @param filename filename to parse.
     */
    public void setFilename(String filename);


    /**
     * Returns the name of the file.
     *
     * @return The currently processed filename.
     */
    public String getFilename();


    /**
     * Returns the root node of the generated parse tree.
     *
     * @return root node of the generated parse tree.
     */
    public AST getParseTree();


    /**
     * Sets the token buffer of the parser.
     *
     * @param buffer buffer to use.
     */
    public void setTokenBuffer(TokenBuffer buffer);


    /**
     * Returns the token names of the parser.
     *
     * @return The token names of the parser.
     */
    public String[] getTokenNames();


    /**
     * Start parsing.
     *
     * @throws RecognitionException if a problem with the input occured.
     * @throws TokenStreamException if something went wrong while generating the stream
     *         of tokens.
     */
    public void parse()
      throws RecognitionException, TokenStreamException;


    /**
     * Resets the parser state.
     */
    public void reset();
}
