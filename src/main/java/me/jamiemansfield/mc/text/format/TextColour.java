/*
 * This file is part of text, licensed under the MIT License (MIT).
 *
 * Copyright (c) Jamie Mansfield <https://www.jamierocks.uk/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.jamiemansfield.mc.text.format;

import me.jamiemansfield.mc.text.Text;

import java.util.Objects;

/**
 * Represents a colour that can be applied to some {@link Text}.
 *
 * <p>
 *     The colours within this classes conform to the names set out by wiki.vg,
 *     and to British spelling. See <a href="http://wiki.vg/Chat#Colors">wiki.vg</a>
 *     for more information on naming.
 * </p>
 */
public final class TextColour {

    /**
     * Should NONE be applied to some {@link Text}, it will take the colour of its
     * parent, or the default colour.
     */
    public static final TextColour NONE = new TextColour("none");

    /**
     * A special TextColour, provided by Minecraft representing the default colour
     * (may vary in different situations).
     */
    public static final TextColour RESET = new TextColour("reset");

    public static final TextColour BLACK = new TextColour("black");
    public static final TextColour DARK_BLUE = new TextColour("dark_blue");
    public static final TextColour DARK_GREEN = new TextColour("dark_green");
    public static final TextColour DARK_CYAN = new TextColour("dark_aqua");
    public static final TextColour DARK_RED = new TextColour("dark_red");
    public static final TextColour PURPLE = new TextColour("dark_purple");
    public static final TextColour GOLD = new TextColour("gold");
    public static final TextColour GREY = new TextColour("gray");
    public static final TextColour DARK_GREY = new TextColour("dark_gray");
    public static final TextColour BLUE = new TextColour("blue");
    public static final TextColour BRIGHT_GREEN = new TextColour("green");
    public static final TextColour CYAN = new TextColour("aqua");
    public static final TextColour RED = new TextColour("red");
    public static final TextColour PINK = new TextColour("light_purple");
    public static final TextColour YELLOW = new TextColour("yellow");
    public static final TextColour WHITE = new TextColour("white");

    private final String internalName;

    private TextColour(final String internalName) {
        this.internalName = internalName;
    }

    /**
     * Gets the internal name of the colour.
     * The internal name being the identifier that Mojang have given to the colour
     * and is used to identify the colour when being serialised and de-serialised.
     *
     * @return The internal name
     */
    public String getInternalName() {
        return this.internalName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getInternalName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextColour)) {
            return false;
        }

        final TextColour that = (TextColour) obj;
        return this.internalName.equals(that.internalName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.internalName);
    }

}
