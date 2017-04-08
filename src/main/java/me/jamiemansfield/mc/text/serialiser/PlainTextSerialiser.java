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

package me.jamiemansfield.mc.text.serialiser;

import me.jamiemansfield.mc.text.LiteralText;
import me.jamiemansfield.mc.text.Text;

/**
 * The {@link TextSerialiser} for just plain text, no formatting or translatable text here.
 */
public final class PlainTextSerialiser extends TextSerialiser {

    @Override
    public String serialise(final Text obj) {
        if (!(obj instanceof LiteralText)) {
            return "";
        }
        final LiteralText text = (LiteralText) obj;

        if (!obj.hasChildren()) {
            return text.getContent();
        }

        final StringBuilder transitiveContent = new StringBuilder();
        transitiveContent.append(text.getContent());

        text.getChildren()
                .forEach(t -> transitiveContent.append(this.serialise(t)));

        return transitiveContent.toString();
    }

    @Override
    public Text deserialise(final String obj) {
        return Text.builder(obj)
                .build();
    }

}
