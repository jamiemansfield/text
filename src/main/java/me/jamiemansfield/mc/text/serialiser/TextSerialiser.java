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

import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.Sets;
import me.jamiemansfield.mc.text.Text;
import me.jamiemansfield.mc.text.event.ClickEvent;
import me.jamiemansfield.mc.text.event.HoverEvent;
import me.jamiemansfield.mc.text.format.TextColour;
import me.jamiemansfield.mc.text.format.TextDecoration;

import java.util.Collections;
import java.util.Set;

/**
 * Represents a serialiser (and deserialiser) that can be used to translate
 * between a {@link String} and a {@link Text}.
 */
public abstract class TextSerialiser {

    static {
        Registry.DECORATION.register(
                TextDecoration.OBFUSCATED,
                TextDecoration.BOLD,
                TextDecoration.STRIKETHROUGH,
                TextDecoration.UNDERLINED,
                TextDecoration.ITALIC,
                TextDecoration.RESET
        );
        Registry.COLOUR.register(
                TextColour.RESET,
                TextColour.BLACK,
                TextColour.DARK_BLUE,
                TextColour.DARK_GREEN,
                TextColour.DARK_CYAN,
                TextColour.DARK_RED,
                TextColour.PURPLE,
                TextColour.GOLD,
                TextColour.GREY,
                TextColour.DARK_GREY,
                TextColour.BLUE,
                TextColour.BRIGHT_GREEN,
                TextColour.CYAN,
                TextColour.RED,
                TextColour.PINK,
                TextColour.YELLOW,
                TextColour.WHITE
        );

        Registry.CLICK_EVENT_ACTION.register(
                ClickEvent.Action.OPEN_URL,
                ClickEvent.Action.RUN_COMMAND,
                ClickEvent.Action.SUGGEST_COMMAND,
                ClickEvent.Action.CHANGE_PAGE
        );
        Registry.HOVER_EVENT_ACTION.register(
                HoverEvent.Action.SHOW_TEXT,
                HoverEvent.Action.SHOW_ITEM,
                HoverEvent.Action.SHOW_ENTITY,
                HoverEvent.Action.SHOW_ACHIEVEMENT
        );
    }

    /**
     * Serialises the given {@link Text} to a {@link String}.
     *
     * @param obj The text to serialise
     * @return The serialised text
     */
    public abstract String serialise(final Text obj);

    /**
     * De-serialises the given {@link String} to a {@link Text}.
     *
     * @param obj The text to de-serialise
     * @return The de-serialised text
     * @throws TextParseException If de-serialisation fails
     */
    public abstract Text deserialise(final String obj) throws TextParseException;

    public static final class Registry<T> {

        // Formatting
        public static final Registry<TextDecoration> DECORATION = new Registry<>();
        public static final Registry<TextColour> COLOUR = new Registry<>();

        // Events
        public static final Registry<ClickEvent.Action> CLICK_EVENT_ACTION = new Registry<>();
        public static final Registry<HoverEvent.Action> HOVER_EVENT_ACTION = new Registry<>();

        private final Set<T> entries = Sets.newHashSet();

        Registry() {
        }

        /**
         * Registers the given object(s), with the registry.
         *
         * @param objs The object(s)
         */
        public void register(T... objs) {
            for (final T obj : objs) {
                checkState(!this.entries.contains(obj), "The given object has already been registered!");
                this.entries.add(obj);
            }
        }

        /**
         * Gets an immutable-view of the registry entries.
         *
         * @return The entries
         */
        public Set<T> getEntries() {
            return Collections.unmodifiableSet(this.entries);
        }

    }

}
