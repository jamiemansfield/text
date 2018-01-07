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
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a translatable text object within Minecraft.
 *
 * <p>
 *     The client will perform the translation, so it must be aware of how to do so!
 *     It is possible to add custom translations via the use of server resource packs.
 * </p>
 *
 * <p>
 *     As with all Text objects within text, this is immutable.
 * </p>
 */
public final class TranslatableText extends Text {

    /**
     * Returns a builder that can be used to create a translatable text object.
     *
     * @return A translatable text builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns a builder that can be used to create a translatable text object,
     * pre-filled with the given key and arguments.
     *
     * @return A translatable text builder
     */
    public static Builder builder(final String key, final Text... args) {
        return new Builder(key, Arrays.asList(args));
    }

    private final String key;
    private final List<Text> args;

    TranslatableText(final Builder builder) {
        super(builder);
        this.key = builder.key;
        this.args = builder.args;
    }

    /**
     * Returns the translation key of the translatable text.
     *
     * @return The translation key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns an {@link List} of the arguments of the translatable text.
     *
     * @return The arguments
     */
    public List<Text> getArgs() {
        return Lists.newArrayList(this.args);
    }

    /**
     * Returns whether the text has any arguments.
     *
     * @return {@code True} if the text has any arguments, {@code false} otherwise
     */
    public boolean hasArguments() {
        return !this.args.isEmpty();
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    MoreObjects.ToStringHelper getStringHelper() {
        return super.getStringHelper()
                .add("key", this.key)
                .add("args", this.args);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TranslatableText) || !super.equals(obj)) return false;

        final TranslatableText that = (TranslatableText) obj;
        return Objects.equals(this.key, that.key) &&
                Objects.equals(this.args, that.args);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.args, this.decorations, this.colour, this.insertion, this.children);
    }

    public static final class Builder extends Text.Builder<TranslatableText, TranslatableText.Builder> {

        String key = "";
        final List<Text> args;

        Builder() {
            this.args = Lists.newArrayList();
        }

        Builder(final String key, final List<Text> args) {
            this.key = key;
            this.args = args;
        }

        Builder(final TranslatableText text) {
            this.key = text.key;
            this.args = Lists.newArrayList(text.args);
            this.decorations.putAll(text.decorations);
            this.colour = text.colour;
            this.insertion = text.insertion;
            this.clickEvent = text.clickEvent;
            this.hoverEvent = text.hoverEvent;
            this.children.addAll(text.children);
        }

        /**
         * Sets the translation key to the given key.
         *
         * @param key The translation key
         * @return The builder
         */
        public Builder key(final String key) {
            this.key = key;
            return this;
        }

        /**
         * Adds the given argument to the arguments.
         *
         * @param argument The argument
         * @return The builder
         */
        public Builder argument(final Text argument) {
            this.args.add(argument);
            return this;
        }

        @Override
        public TranslatableText build() {
            return new TranslatableText(this);
        }

    }

}
