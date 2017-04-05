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
import com.google.common.collect.Maps;
import me.jamiemansfield.mc.text.format.TextColour;
import me.jamiemansfield.mc.text.format.TextDecoration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a text object within Minecraft.
 *
 * <p>
 *     It should be noted that this is an immutable object!
 * </p>
 */
public abstract class Text {

    /**
     * Returns a builder that can be used to create some text.
     *
     * @return A text builder
     */
    public static LiteralText.Builder builder() {
        return new LiteralText.Builder();
    }

    /**
     * Returns a builder that can be used to create some text, pre-filled with the given content.
     *
     * @param content The content
     * @return A text builder
     */
    public static LiteralText.Builder builder(final String content) {
        return new LiteralText.Builder(content);
    }

    /**
     * Returns a builder that can be used to create a translatable text object.
     *
     * @return A translatable text builder
     */
    public static TranslatableText.Builder translatableBuilder() {
        return new TranslatableText.Builder();
    }

    /**
     * Returns a builder that can be used to create a translatable text object, pre-filled with the given key
     * and arguments.
     *
     * @return A translatable text builder
     */
    public static TranslatableText.Builder translatableBuilder(final String key, final Text... args) {
        return new TranslatableText.Builder(key, Arrays.asList(args));
    }

    final Map<TextDecoration, Boolean> decorations;
    final TextColour colour;
    final List<Text> children;

    Text(final Map<TextDecoration, Boolean> decorations, final TextColour colour, final List<Text> children) {
        this.decorations = decorations;
        this.colour = colour;
        this.children = children;
    }

    /**
     * Returns weather the text is bold.
     *
     * @return {@code True} if the text is bold, {@code false} otherwise
     */
    public boolean isBold() {
        return this.hasDecoration(TextDecoration.BOLD);
    }

    /**
     * Returns weather the text is italic.
     *
     * @return {@code True} if the text is italic, {@code false} otherwise
     */
    public boolean isItalic() {
        return this.hasDecoration(TextDecoration.ITALIC);
    }

    /**
     * Returns weather the text is underlined.
     *
     * @return {@code True} if the text is underlined, {@code false} otherwise
     */
    public boolean isUnderlined() {
        return this.hasDecoration(TextDecoration.UNDERLINED);
    }

    /**
     * Returns weather the text is strikethrough.
     *
     * @return {@code True} if the text is strikethrough, {@code false} otherwise
     */
    public boolean isStrikethrough() {
        return this.hasDecoration(TextDecoration.STRIKETHROUGH);
    }

    /**
     * Returns weather the text is obfuscated.
     *
     * @return {@code True} if the text is obfuscated, {@code false} otherwise
     */
    public boolean isObfuscated() {
        return this.hasDecoration(TextDecoration.OBFUSCATED);
    }

    /**
     * Returns all of the {@link TextDecoration}s for the text.
     *
     * @return The decorations
     */
    public Map<TextDecoration, Boolean> getDecorations() {
        return Maps.newHashMap(this.decorations);
    }

    /**
     * Returns weather the text has the given {@link TextDecoration} applied.
     *
     * @param decoration The decoration
     * @return {@code True} if the text has the given decoration applied, {@code false} otherwise
     */
    public boolean hasDecoration(final TextDecoration decoration) {
        return this.decorations.getOrDefault(decoration, false);
    }

    /**
     * Returns the colour of the text.
     *
     * @return The colour
     */
    public TextColour getColour() {
        return this.colour;
    }

    /**
     * Returns all of the children of the text.
     *
     * @return The children
     */
    public List<Text> getChildren() {
        return Lists.newArrayList(this.children);
    }

    /**
     * Returns weather the text has any children.
     *
     * @return {@code True} if the text has any children, {@code false} otherwise
     */
    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    /**
     * Returns weather the text contains the given {@link Text} object as a child.
     *
     * @param text The given text
     * @return {@code True} if the text has the given text as a child, {@code false} otherwise
     */
    public boolean contains(final Text text) {
        return this.children.contains(text);
    }

    /**
     * Creates a text builder from the text.
     *
     * @return A text builder
     */
    public abstract Text.Builder toBuilder();

    MoreObjects.ToStringHelper getStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("decorations", this.decorations)
                .add("colour", this.colour)
                .add("children", this.children);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.getStringHelper().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Text)) {
            return false;
        }

        final Text that = (Text) obj;
        return this.decorations.equals(that.decorations) &&
                this.colour.equals(that.colour) &&
                this.children.equals(that.children);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.decorations, this.colour, this.children);
    }

    /**
     * A builder that can be used to create some {@link Text}.
     */
    public static abstract class Builder {

        final Map<TextDecoration, Boolean> decorations = Maps.newHashMap();
        TextColour colour = TextColour.NONE;
        final List<Text> children = Lists.newArrayList();

        Builder() {
        }

        /**
         * Applies a given {@link TextDecoration} to the text.
         *
         * @param decoration The decoration to apply
         * @return The builder
         */
        public Builder apply(final TextDecoration decoration) {
            return this.apply(decoration, true);
        }

        /**
         * Un-applies a given {@link TextDecoration} from the text.
         *
         * @param decoration The decoration to unapply
         * @return The builder
         */
        public Builder unapply(final TextDecoration decoration) {
            return this.apply(decoration, false);
        }

        /**
         * Applies, or un-applies dependant on given parameters, a given {@link TextDecoration} to
         * the text.
         *
         * @param decoration The decoration to apply (or not)
         * @param active Weather to apply the decoration
         * @return The builder
         */
        public Builder apply(final TextDecoration decoration, final Boolean active) {
            if (decoration == TextDecoration.RESET) {
                this.decorations.clear();
                this.colour = TextColour.NONE;
            } else if (active == null) {
                this.decorations.remove(decoration);
            } else {
                this.decorations.put(decoration, active);
            }

            return this;
        }

        /**
         * Applies a given {@link TextColour} to the text.
         *
         * @param colour The colour to apply
         * @return The builder
         */
        public Builder apply(final TextColour colour) {
            this.colour = colour;
            return this;
        }

        /**
         * Adds a child {@link Text} object to the text.
         *
         * @param child The child text object
         * @return The builder
         */
        public Builder append(final Text child) {
            this.children.add(child);
            return this;
        }

        /**
         * Creates a {@link Text} object based on the values provided to the builder.
         *
         * @return A text object
         */
        public abstract Text build();

    }

}
