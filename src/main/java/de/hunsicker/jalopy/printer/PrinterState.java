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
import java.util.LinkedList;
import java.util.List;


/**
 * Holds some state values during the printing process (mostly used to implement line
 * wrapping and aligning).
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.5 $
 */
final class PrinterState
{
    //~ Instance variables ---------------------------------------------------------------

    LinkedList parenScope;

    /**
     * Holds the right parentheses of parentheses pairs that should be wrapped and
     * indented.
     */
    List parentheses;

    /** An array we use to hold the arguments for String formatting. */
    final Object[] args = new Object[6];

    /** Our markers. */
    Markers markers;

    /** Indicates that the printer currently prints an anonymous inner class. */
    boolean anonymousInnerClass;

    /**
     * Indicates that we're currently printing the expressions of an expression list (for
     * <code>if</code>, <code>while</code>, <code>do-while</code> blocks).
     */
    boolean expressionList;

    /**
     * Indicates wether a newline was printed before the last <code>extends</code>
     * keyword.
     */
    boolean extendsWrappedBefore;

    /** Indicates that the printer currently prints an inner class. */
    boolean innerClass;

    /**
     * Indicates whether we should print the left curly brace of method/ctor declarations
     * in C-style.
     */
    boolean newlineBeforeLeftBrace;

    /**
     * Indicates that we're currently printing the parameters of a parameter list (for
     * method calls or creators).
     */
    boolean paramList;

    /**
     * Indicates whether the parameter list of a method or ctor declaration was wrapped.
     */
    boolean parametersWrapped;

    /**
     * Indicates whether operators should be wrapped as needed, or wrapping should be
     * forced after each operator.
     */
    boolean wrap;

    /** Holds the current level of array brackets. */
    int arrayBrackets;

    /** Holds the column offset of the rightmost assignment for assignment aligning. */
    int assignOffset = AssignmentPrinter.OFFSET_NONE;

    /** Stores the nesting level for parameter/expression lists. */
    int paramLevel;

    /** Holds the column offset of the rightmost identifier for parameter aligning. */
    int paramOffset = ParametersPrinter.OFFSET_NONE;

    /**
     * Holds the column offset of the rightmost variable definition for variable
     * aligning.
     */
    int variableOffset = VariableDeclarationPrinter.OFFSET_NONE;
    boolean smallIndent = false;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new PrinterState object.
     *
     * @param writer the NodeWriter to associate.
     */
    PrinterState(NodeWriter writer)
    {
        if (writer.mode == NodeWriter.MODE_DEFAULT)
        {
            this.markers = new Markers(writer);
            this.parenScope = new LinkedList();
            this.parentheses = new ArrayList(5);
            this.parenScope.addFirst(new ParenthesesScope(0));
        }
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    public void dispose()
    {
        if (this.parenScope != null)
        {
            this.parenScope.clear();
            this.markers = null;
        }
    }
    
    public void reset() {
        if (this.parenScope!=null){
            //while(this.parenScope.size()>1)
            //    this.parenScope.removeLast();
            this.parenScope.clear();
            this.parenScope.addFirst(new ParenthesesScope(0));
        }
        if (this.parentheses!=null){
            this.parentheses.clear();
        }
        if (this.markers!=null){
            this.markers.reset();
        }
        this.wrap=false;
        this.anonymousInnerClass=false;
        this.expressionList = false;
        this.extendsWrappedBefore=false;
        this.innerClass=false;
        this.newlineBeforeLeftBrace=false;
        this.paramList=false;
        this.parametersWrapped=false;
        this.wrap=false;
        this.arrayBrackets=0;
        this.assignOffset = AssignmentPrinter.OFFSET_NONE;
        this.paramLevel = 0;
        this.paramOffset = ParametersPrinter.OFFSET_NONE;
        this.variableOffset = VariableDeclarationPrinter.OFFSET_NONE;        
        smallIndent=false;

    }
    public void reset(PrinterState state) {
        this.anonymousInnerClass = state.anonymousInnerClass;
        this.innerClass = state.innerClass;
        this.newlineBeforeLeftBrace = state.newlineBeforeLeftBrace;
        this.parametersWrapped=state.parametersWrapped;
        this.wrap=state.wrap;
        smallIndent=false;
    }
}
