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

package me.jamiemansfield.mc.text;

import com.google.common.base.MoreObjects;
import me.jamiemansfield.mc.text.format.TextColour;
import me.jamiemansfield.mc.text.format.TextDecoration;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a literal text object within Minecraft. This simply means that
 * the text has a {@link String} contents and nothing else.
 *
 * <p>
 *     As with all Text objects within FlintAPI, this is immutable.
 * </p>
 */
public final class LiteralText extends Text {

    private final String content;

    private LiteralText(final String content, final Map<TextDecoration, Boolean> decorations, final TextColour colour, final List<Text> children) {
        super(decorations, colour, children);
        this.content = content;
    }

    /**
     * Returns the {@link String} content of <b>only</b> this {@link Text} object.
     *
     * @return The content
     */
    public String getContent() {
        return this.getContent(false);
    }

    /**
     * Returns the {@link String} content of this {@link Text} object, and its children if the
     * user should so choice to do so.
     *
     * @param transitively {@code true} if the user would like the full content from all of the
     *                     children too.
     * @return The content
     */
    public String getContent(boolean transitively) {
        if (!transitively || !this.hasChildren()) {
            return this.content;
        }

        final StringBuilder transitiveContent = new StringBuilder();
        transitiveContent.append(this.content);

        this.children.stream()
                .filter(text -> text instanceof LiteralText)
                .map(text -> (LiteralText) text)
                .forEach(text -> transitiveContent.append(text.getContent(true)));

        return transitiveContent.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LiteralText.Builder toBuilder() {
        return new LiteralText.Builder(this);
    }

    @Override
    MoreObjects.ToStringHelper getStringHelper() {
        return super.getStringHelper()
                .add("content", this.content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LiteralText) || !super.equals(obj)) {
            return false;
        }

        final LiteralText that = (LiteralText) obj;
        return this.content.equals(that.content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.content, this.decorations, this.colour, this.children);
    }

    public static final class Builder extends Text.Builder {

        String content = "";

        Builder() {
        }

        Builder(final String content) {
            this.content = content;
        }

        Builder(final LiteralText text) {
            this.content = text.content;
            this.decorations.putAll(text.decorations);
            this.colour = text.colour;
            this.children.addAll(text.children);
        }

        /**
         * Sets the content to the content provided.
         *
         * @param content The content
         * @return The builder
         */
        public Builder content(final String content) {
            this.content = content;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder apply(TextDecoration decoration) {
            return (Builder) super.apply(decoration);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder unapply(TextDecoration decoration) {
            return (Builder) super.unapply(decoration);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder apply(TextDecoration decoration, Boolean active) {
            return (Builder) super.apply(decoration, active);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder apply(TextColour colour) {
            return (Builder) super.apply(colour);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder append(Text child) {
            return (Builder) super.append(child);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LiteralText build() {
            return new LiteralText(this.content, this.decorations, this.colour, this.children);
        }

    }

}
