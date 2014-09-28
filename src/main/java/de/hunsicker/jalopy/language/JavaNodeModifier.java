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

import java.lang.reflect.Modifier;

import de.hunsicker.jalopy.language.antlr.JavaTokenTypes;

import antlr.collections.AST;


/**
 * Helper class which resolves the different modifiers of a MODIFIERS node.
 * 
 * <p>
 * This class can be used to get the access level of a class, method, field...
 * </p>
 *
 * @author <a href="http://jalopy.sf.net/contact.html">Marco Hunsicker</a>
 * @version $Revision: 1.5 $
 *
 * @todo add constant for default (friendly) access
 */
public final class JavaNodeModifier
    extends Modifier
{
    //~ Static variables/initializers ----------------------------------------------------

    /** Unknown keyword. */
    public static final int UNKNOWN = 0;

    //~ Constructors ---------------------------------------------------------------------

    /**
     * Creates a new JavaNodeModifier object.
     */
    private JavaNodeModifier()
    {
    }

    //~ Methods --------------------------------------------------------------------------

    /**
     * Indicates whether the given modifiers node contains the <em>abstract</em> keyword.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the given node contains the <em>abstract</em>
     *         keyword.
     */
    public static boolean isAbstract(AST modifiers)
    {
        return Modifier.isAbstract(valueOf(modifiers));
    }


    /**
     * Indicates whether the given modifiers node contains the <em>final</em> keyword.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the given node contains the <em>final</em> keyword.
     */
    public static boolean isFinal(AST modifiers)
    {
        return Modifier.isFinal(valueOf(modifiers));
    }


    /**
     * Indicates whether the given modifiers node denotes package protected (friendly)
     * access.
     *
     * @param modifiers
     *
     * @return <code>true</code> if the node denotes package protected access.
     */
    public static boolean isFriendly(AST modifiers)
    {
        return isFriendly(valueOf(modifiers));
    }


    /**
     * Indicates whether the given modifier denotes package protected (friendly) access.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the node denotes package protected access.
     */
    public static boolean isFriendly(int modifiers)
    {
        return (modifiers & (Modifier.PUBLIC + Modifier.PROTECTED + Modifier.PRIVATE)) == 0;
    }


    /**
     * Indicates whether the given modifiers node contains the <em>private</em> keyword.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the given node contains the <em>private</em> keyword.
     */
    public static boolean isPrivate(AST modifiers)
    {
        return Modifier.isPrivate(valueOf(modifiers));
    }


    /**
     * Indicates whether the given modifiers node contains the <em>protected</em>
     * keyword.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the given node contains the <em>protected</em>
     *         keyword.
     */
    public static boolean isProtected(AST modifiers)
    {
        return Modifier.isProtected(valueOf(modifiers));
    }


    /**
     * Indicates whether the given modifiers node contains the <em>public</em> keyword.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the given node contains the <em>public</em> keyword.
     */
    public static boolean isPublic(AST modifiers)
    {
        return Modifier.isPublic(valueOf(modifiers));
    }


    /**
     * Indicates whether the given modifiers node contains the <em>static</em> keyword.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the given node contains the <em>static</em> keyword.
     */
    public static boolean isStatic(AST modifiers)
    {
        return Modifier.isStatic(valueOf(modifiers));
    }


    /**
     * Indicates whether the given modifiers node contains the <em>synchronized</em>
     * keyword.
     *
     * @param modifiers MODIFIERS node to check.
     *
     * @return <code>true</code> if the given node contains the <em>synchronized</em>
     *         keyword.
     */
    public static boolean isSynchronized(AST modifiers)
    {
        return Modifier.isSynchronized(valueOf(modifiers));
    }


    /**
     * Returns the modifier mask of the given modifiers node.
     *
     * @param modifiers MODIFIERS node or one of the valid nodes representing modifiers
     *        (e.g. LITERAL_public, LITERAL_synchronized etc.).
     *
     * @return modifier mask of the given modifiers.
     *
     * @throws IllegalArgumentException if <code>modifiers == null</code> or
     *         <code>modifiers.getType() != JavaTokenTypes.MODIFIERS</code>
     */
    public static int valueOf(AST modifiers)
    {
        switch (modifiers.getType())
        {
            case JavaTokenTypes.MODIFIERS :
                break;

            case JavaTokenTypes.LITERAL_public :
            case JavaTokenTypes.ENUM_CONSTANT_DEF:
                return Modifier.PUBLIC;

            case JavaTokenTypes.LITERAL_protected :
                return Modifier.PROTECTED;

            case JavaTokenTypes.LITERAL_private :
                return Modifier.PRIVATE;

            case JavaTokenTypes.LITERAL_static :
                return Modifier.STATIC;

            case JavaTokenTypes.ABSTRACT :
                return Modifier.ABSTRACT;

            case JavaTokenTypes.FINAL :
                return Modifier.FINAL;

            case JavaTokenTypes.LITERAL_native :
                return Modifier.NATIVE;

            case JavaTokenTypes.LITERAL_transient :
                return Modifier.TRANSIENT;

            case JavaTokenTypes.LITERAL_synchronized :
                return Modifier.SYNCHRONIZED;

            case JavaTokenTypes.STRICTFP :
                return Modifier.STRICT;

            case JavaTokenTypes.LITERAL_volatile :
                return Modifier.VOLATILE;

            default :
                modifiers =
                    JavaNodeHelper.getFirstChild(modifiers, JavaTokenTypes.MODIFIERS);

                if (
                    (modifiers == null)
                    || (modifiers.getType() != JavaTokenTypes.MODIFIERS))
                {
                    throw new IllegalArgumentException(
                        "no valid modidifier -- " + modifiers);
                }

                break;
        }

        int mod = 0;

        for (AST sib = modifiers.getFirstChild(); sib != null;
            sib = sib.getNextSibling())
        {
            switch (sib.getType())
            {
                case JavaTokenTypes.LITERAL_public :
                    mod += Modifier.PUBLIC;

                    break;

                case JavaTokenTypes.LITERAL_protected :
                    mod += Modifier.PROTECTED;

                    break;

                case JavaTokenTypes.LITERAL_private :
                    mod += Modifier.PRIVATE;

                    break;

                case JavaTokenTypes.LITERAL_static :
                    mod += Modifier.STATIC;

                    break;

                case JavaTokenTypes.ABSTRACT :
                    mod += Modifier.ABSTRACT;

                    break;

                case JavaTokenTypes.FINAL :
                    mod += Modifier.FINAL;

                    break;

                case JavaTokenTypes.LITERAL_synchronized :
                    mod += Modifier.SYNCHRONIZED;

                    break;

                case JavaTokenTypes.LITERAL_interface :
                    mod += Modifier.INTERFACE;

                    break;

                case JavaTokenTypes.LITERAL_native :
                    mod += Modifier.NATIVE;

                    break;

                case JavaTokenTypes.LITERAL_transient :
                    mod += Modifier.TRANSIENT;

                    break;

                case JavaTokenTypes.STRICTFP :
                    mod += Modifier.STRICT;

                    break;

                case JavaTokenTypes.LITERAL_volatile :
                    mod += Modifier.VOLATILE;

                    break;
            }
        }

        return mod;
    }
}
