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
    public static final TextColour NONE = of("none");

    /**
     * A special TextColour, provided by Minecraft representing the default colour
     * (may vary in different situations).
     */
    public static final TextColour RESET = of("reset");

    public static final TextColour BLACK = of("black");
    public static final TextColour DARK_BLUE = of("dark_blue");
    public static final TextColour DARK_GREEN = of("dark_green");
    public static final TextColour DARK_CYAN = of("dark_aqua");
    public static final TextColour DARK_RED = of("dark_red");
    public static final TextColour PURPLE = of("dark_purple");
    public static final TextColour GOLD = of("gold");
    public static final TextColour GREY = of("gray");
    public static final TextColour DARK_GREY = of("dark_gray");
    public static final TextColour BLUE = of("blue");
    public static final TextColour BRIGHT_GREEN = of("green");
    public static final TextColour CYAN = of("aqua");
    public static final TextColour RED = of("red");
    public static final TextColour PINK = of("light_purple");
    public static final TextColour YELLOW = of("yellow");
    public static final TextColour WHITE = of("white");

    /**
     * Creates a text colour object backed by the given internal name.
     *
     * This is provided as to allow extra colours to be used should text
     * be used in an environment which isn't Minecraft, or just more colours
     * are available.
     *
     * @param internalName The internal name of the colour
     * @return The colour
     */
    public static TextColour of(final String internalName) {
        return new TextColour(internalName);
    }

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

    @Override
    public String toString() {
        return this.getInternalName();
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(this.internalName);
    }

}
