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

package me.jamiemansfield.mc.text.event;

import com.google.common.base.MoreObjects;
import me.jamiemansfield.mc.text.Text;

import java.util.Objects;

/**
 * Represents the reaction to a {@link Text} being hovered over.
 */
public final class HoverEvent {

    private final Action action;
    private final Text value;

    public HoverEvent(final Action action, final Text value) {
        this.action = action;
        this.value = value;
    }

    /**
     * Gets the action to be performed upon the text being hovered
     * over.
     *
     * @return The action
     */
    public Action getAction() {
        return this.action;
    }

    /**
     * Gets a {@link Text} value that is used in conjunction with
     * the {@link Action} when the text is hovered over.
     *
     * @return The value
     */
    public Text getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("action", this.action)
                .add("value", this.value)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HoverEvent)) {
            return false;
        }
        final HoverEvent that = (HoverEvent) obj;

        return Objects.equals(this.value, that.value) &&
                Objects.equals(this.action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.action);
    }

    /**
     * Represents the action that will be taken when a {@link Text}
     * is hovered over.
     */
    public static final class Action {

        public static final Action SHOW_TEXT = of("show_text");
        public static final Action SHOW_ITEM = of("show_item");
        public static final Action SHOW_ENTITY = of("show_entity");
        public static final Action SHOW_ACHIEVEMENT = of("show_achievement");

        /**
         * Creates a hover event action object backed by the given internal name.
         *
         * This is provided as to allow extra actions to be used should text
         * be used in an environment which isn't Minecraft, or just more actions
         * are available.
         *
         * @param internalName The internal name of the action
         * @return The action
         */
        public static Action of(final String internalName) {
            return new Action(internalName);
        }

        private final String internalName;

        private Action(final String internalName) {
            this.internalName = internalName;
        }

        /**
         * Gets the internal name of the action.
         * The internal name being the identifier that Mojang have given to the action
         * and is used to identify the action when being serialised and de-serialised.
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
            if (!(obj instanceof Action)) {
                return false;
            }

            final Action that = (Action) obj;
            return this.internalName.equals(that.internalName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.internalName);
        }

    }

}
