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

    public static final TextDecoration OBFUSCATED = new TextDecoration("obfuscated");
    public static final TextDecoration BOLD = new TextDecoration("bold");
    public static final TextDecoration STRIKETHROUGH = new TextDecoration("strikethrough");
    public static final TextDecoration UNDERLINED = new TextDecoration("underline");
    public static final TextDecoration ITALIC = new TextDecoration("italic");
    public static final TextDecoration RESET = new TextDecoration("reset");

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
        if (!(obj instanceof TextDecoration)) {
            return false;
        }

        final TextDecoration that = (TextDecoration) obj;
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
