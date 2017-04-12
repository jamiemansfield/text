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
 * Represents a decorative style that can be applied to some {@link Text}.
 */
public final class TextDecoration {

    /**
     * Should RESET be applied to some {@link Text}, it will remove any colours and
     * any decorations already applied.
     */
    public static final TextDecoration RESET = of("reset");

    public static final TextDecoration OBFUSCATED = of("obfuscated");
    public static final TextDecoration BOLD = of("bold");
    public static final TextDecoration STRIKETHROUGH = of("strikethrough");
    public static final TextDecoration UNDERLINED = of("underline");
    public static final TextDecoration ITALIC = of("italic");

    /**
     * Creates a text decoration object backed by the given internal name.
     *
     * This is provided as to allow extra styles to be used should text
     * be used in an environment which isn't Minecraft, or just more styles
     * are available.
     *
     * @param internalName The internal name of the style
     * @return The decoration
     */
    public static TextDecoration of(final String internalName) {
        return new TextDecoration(internalName);
    }

    private final String internalName;

    private TextDecoration(final String internalName) {
        this.internalName = internalName;
    }

    /**
     * Gets the internal name of the decorative style.
     * The internal name being the identifier that Mojang have given to the style
     * and is used to identify the style when being serialised and de-serialised.
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
        if (!(obj instanceof TextDecoration)) {
            return false;
        }

        final TextDecoration that = (TextDecoration) obj;
        return this.internalName.equals(that.internalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.internalName);
    }

}
