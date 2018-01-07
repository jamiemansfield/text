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

import java.util.Objects;

/**
 * Represents a literal text object within Minecraft. This simply means that
 * the text has a {@link String} contents and nothing else.
 *
 * <p>
 *     As with all Text objects within text, this is immutable.
 * </p>
 */
public final class LiteralText extends Text {

    /**
     * Returns a builder that can be used to create a literal text object.
     *
     * @return A literal text builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns a builder that can be used to create a literal text object,
     * pre-filled with the given content.
     *
     * @param content The content
     * @return A literal text builder
     */
    public static Builder builder(final String content) {
        return new Builder(content);
    }

    private final String content;

    private LiteralText(final Builder builder) {
        super(builder);
        this.content = builder.content;
    }

    /**
     * Returns the {@link String} content of this {@link Text} object.
     *
     * @return The content
     */
    public String getContent() {
        return this.content;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    MoreObjects.ToStringHelper getStringHelper() {
        return super.getStringHelper()
                .add("content", this.content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LiteralText) || !super.equals(obj)) return false;

        final LiteralText that = (LiteralText) obj;
        return Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.content);
    }

    public static final class Builder extends Text.Builder<LiteralText, LiteralText.Builder> {

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
            this.insertion = text.insertion;
            this.clickEvent = text.clickEvent;
            this.hoverEvent = text.hoverEvent;
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

        @Override
        public LiteralText build() {
            return new LiteralText(this);
        }

    }

}
