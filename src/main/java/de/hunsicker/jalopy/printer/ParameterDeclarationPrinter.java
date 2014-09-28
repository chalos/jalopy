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

import java.io.IOException;

import antlr.collections.AST;
import de.hunsicker.jalopy.language.antlr.JavaNode;
import de.hunsicker.jalopy.language.antlr.JavaNodeFactory;
import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;
import de.hunsicker.jalopy.storage.ConventionDefaults;
import de.hunsicker.jalopy.storage.ConventionKeys;


/**
 * Printer for parameter declarations [<code>PARAMETER_DEF</code>].
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.4 $
 */
final class ParameterDeclarationPrinter
    extends AbstractPrinter
{
    //~ Static variables/initializers ----------------------------------------------------

    private static final Printer INSTANCE = new ParameterDeclarationPrinter();

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new ParameterDeclarationPrinter object.
     */
    public ParameterDeclarationPrinter()
    {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Returns the sole instance of this class.
     *
     * @return the sole instance of this class.
     */
    public static final Printer getInstance()
    {
        return INSTANCE;
    }


    /**
     * {@inheritDoc}
     */
    public void print(
        AST        node,
        NodeWriter out)
      throws IOException
    {
        AST modifier = node.getFirstChild();

        if (
            (AbstractPrinter.settings.getBoolean(
                ConventionKeys.INSERT_FINAL_MODIFIER_FOR_METHOD_PARAMETERS, 
                ConventionDefaults.INSERT_FINAL_MODIFIER_FOR_METHOD_PARAMETERS)
                && ((JavaNode)node).getParent().getParent().getType() == JavaTokenTypes.METHOD_DEF
                ) ||
                
            AbstractPrinter.settings.getBoolean(
                ConventionKeys.INSERT_FINAL_MODIFIER_FOR_PARAMETERS, 
                ConventionDefaults.INSERT_FINAL_MODIFIER_FOR_PARAMETERS))
        {
            boolean  finalAlreadyExists = false;
            for (
                AST child = modifier.getFirstChild(); child != null;
                child = child.getNextSibling())
            {
                if (child.getType()==JavaTokenTypes.FINAL) {
                    finalAlreadyExists = true;
                    break;
                }
            }
            if (! finalAlreadyExists) {
                AST finalModifier = out.getJavaNodeFactory().create(JavaTokenTypes.FINAL, "final");
                modifier.addChild(finalModifier);
            }
        }
        PrinterFactory.create(modifier, out).print(modifier, out);

        AST type = modifier.getNextSibling();
        PrinterFactory.create(type, out).print(type, out);

        // align the parameter
        if (
            (out.state.paramOffset != ParametersPrinter.OFFSET_NONE)
            && (out.column < out.state.paramOffset))
        {
            out.print(
                out.getString(out.state.paramOffset - out.column), JavaTokenTypes.WS);
        }
        else
        {
            out.print(SPACE, JavaTokenTypes.WS);
        }

        AST identifier = type.getNextSibling();
        PrinterFactory.create(identifier, out).print(identifier, out);
    }
}
