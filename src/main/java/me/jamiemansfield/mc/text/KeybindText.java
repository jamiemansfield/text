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
import me.jamiemansfield.mc.text.event.ClickEvent;
import me.jamiemansfield.mc.text.event.HoverEvent;
import me.jamiemansfield.mc.text.format.TextColour;
import me.jamiemansfield.mc.text.format.TextDecoration;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the keybind text object within Minecraft, allows for keybindings
 * to be specified as part of a text object, but processed client-side.
 *
 * <p>
 *     As with all Text objects within text, this is immutable.
 * </p>
 */
public class KeybindText extends Text {

    private final String keybind;

    private KeybindText(final String keybind, final Map<TextDecoration, Boolean> decorations, final TextColour colour,
            final Optional<String> insertion, final Optional<ClickEvent> clickEvent, final Optional<HoverEvent> hoverEvent,
            final List<Text> children) {
        super(decorations, colour, insertion, clickEvent, hoverEvent, children);
        this.keybind = keybind;
    }

    /**
     * Gets the identifier of the keybinding specified by the text object.
     *
     * @return The keybind
     */
    public String getKeybind() {
        return this.keybind;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    MoreObjects.ToStringHelper getStringHelper() {
        return super.getStringHelper()
                .add("keybind", this.keybind);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof KeybindText) || !super.equals(obj)) return false;

        final KeybindText that = (KeybindText) obj;
        return Objects.equals(this.keybind, that.keybind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.keybind);
    }

    public static final class Builder extends Text.Builder {

        String keybind = "";

        Builder() {
        }

        Builder(final String keybind) {
            this.keybind = keybind;
        }

        Builder(final KeybindText text) {
            this.keybind = text.keybind;
            this.decorations.putAll(text.decorations);
            this.colour = text.colour;
            this.insertion = text.insertion;
            this.clickEvent = text.clickEvent;
            this.hoverEvent = text.hoverEvent;
            this.children.addAll(text.children);
        }

        /**
         * Sets the keybind to the keybind provided.
         *
         * @param keybind The keybind
         * @return The builder
         */
        public Builder keybind(final String keybind) {
            this.keybind = keybind;
            return this;
        }

        @Override
        public Builder apply(TextDecoration decoration) {
            return (Builder) super.apply(decoration);
        }

        @Override
        public Builder unapply(TextDecoration decoration) {
            return (Builder) super.unapply(decoration);
        }

        @Override
        public Builder apply(TextDecoration decoration, Boolean active) {
            return (Builder) super.apply(decoration, active);
        }

        @Override
        public Builder apply(TextColour colour) {
            return (Builder) super.apply(colour);
        }

        @Override
        public Builder append(Text child) {
            return (Builder) super.append(child);
        }

        @Override
        public Builder insertion(final String insertion) {
            return (Builder) super.insertion(insertion);
        }

        @Override
        public Builder click(final ClickEvent clickEvent) {
            return (Builder) super.click(clickEvent);
        }

        @Override
        public Builder hover(final HoverEvent hoverEvent) {
            return (Builder) super.hover(hoverEvent);
        }

        @Override
        public KeybindText build() {
            return new KeybindText(this.keybind, this.decorations, this.colour, this.insertion, this.clickEvent, this.hoverEvent, this.children);
        }

    }

}
