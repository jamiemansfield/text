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

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.jamiemansfield.mc.text.LiteralText;
import me.jamiemansfield.mc.text.Text;
import me.jamiemansfield.mc.text.TranslatableText;
import me.jamiemansfield.mc.text.format.TextColour;
import me.jamiemansfield.mc.text.format.TextDecoration;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Set;

/**
 * The serialiser (and deserialiser) for {@link Text}.
 */
public final class TextSerialiser implements JsonSerializer<Text>, JsonDeserializer<Text> {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(Text.class, new TextSerialiser())
            .create();

    private static final Set<TextDecoration> DECORATIONS = ImmutableSet.<TextDecoration>builder()
            .add(TextDecoration.OBFUSCATED)
            .add(TextDecoration.BOLD)
            .add(TextDecoration.STRIKETHROUGH)
            .add(TextDecoration.UNDERLINED)
            .add(TextDecoration.ITALIC)
            .add(TextDecoration.RESET)
            .build();

    private static final Set<TextColour> COLOURS = ImmutableSet.<TextColour>builder()
            .add(TextColour.RESET)
            .add(TextColour.BLACK)
            .add(TextColour.DARK_BLUE)
            .add(TextColour.DARK_GREEN)
            .add(TextColour.DARK_CYAN)
            .add(TextColour.DARK_RED)
            .add(TextColour.PURPLE)
            .add(TextColour.GOLD)
            .add(TextColour.GREY)
            .add(TextColour.DARK_GREY)
            .add(TextColour.BLUE)
            .add(TextColour.BRIGHT_GREEN)
            .add(TextColour.CYAN)
            .add(TextColour.RED)
            .add(TextColour.PINK)
            .add(TextColour.YELLOW)
            .add(TextColour.WHITE)
            .build();

    /**
     * Serialises the given {@link Text} to a {@link String}.
     *
     * @param text The text to serialise
     * @return The text in string form
     */
    public static String serialise(final Text text) {
        return GSON.toJson(text);
    }

    /**
     * Deserialises the given text to a {@link Text}.
     *
     * @param text The text to deserialise
     * @return The given text in Text form
     */
    public static Text deserialise(final String text) {
        return GSON.fromJson(text, Text.class);
    }

    private TextSerialiser() {
    }

    @Override
    public Text deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("Terribly sorry, but I will not be able to deserialise " + json);
        }
        final JsonObject obj = json.getAsJsonObject();
        final Text.Builder text;

        if (obj.has("text")) {
            text = Text.builder(obj.get("text").getAsString());
        } else if (obj.has("translate")) {
            if (obj.has("with") && obj.get("with").isJsonArray()) {
                final JsonArray with = obj.getAsJsonArray("with");
                final Text[] arguments = new Text[with.size()];

                for (int i = 0; i < with.size(); i ++) {
                    final JsonElement element = with.get(i);
                    arguments[i] = this.deserialize(element, element.getClass(), context);
                }

                text = Text.translatableBuilder(obj.get("translate").getAsString(), arguments);
            } else {
                text = Text.translatableBuilder(obj.get("translate").getAsString());
            }
        } else {
            throw new JsonParseException("Terribly sorry, but I will not be able to deserialise " + json);
        }

        DECORATIONS.stream()
                .filter(decoration -> obj.has(decoration.getInternalName()))
                .forEach(decoration -> text.apply(decoration, obj.get(decoration.getInternalName()).getAsBoolean()));

        if (obj.has("color")) {
            final Optional<TextColour> colour = COLOURS.stream()
                    .filter(clr -> clr.getInternalName().equals(obj.get("color").getAsString()))
                    .findAny();
            if (!colour.isPresent()) {
                throw new JsonParseException("Terribly sorry, but I will not be able to deserialise " + json);
            }
            text.apply(colour.get());
        }

        if (obj.has("insertion")) {
            text.insertion(obj.get("insertion").getAsString());
        }

        if (obj.has("extra")) {
            final JsonArray extra = obj.getAsJsonArray("extra");

            for (int i = 0; i < extra.size(); i ++) {
                final JsonElement element = extra.get(i);
                text.append(this.deserialize(element, element.getClass(), context));
            }
        }

        return text.build();
    }

    @Override
    public JsonElement serialize(Text src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject json = new JsonObject();

        // Lets begin with the basics
        if (src instanceof LiteralText) {
            json.add("text", new JsonPrimitive(((LiteralText) src).getContent()));
        } else if (src instanceof TranslatableText) {
            json.add("translate", new JsonPrimitive(((TranslatableText) src).getKey()));

            if (((TranslatableText) src).hasArguments()) {
                final JsonArray with = new JsonArray();

                ((TranslatableText) src).getArgs().stream()
                        .map(text -> this.serialize(text, text.getClass(), context))
                        .forEach(with::add);

                json.add("with", with);
            }
        }

        // Decorations
        src.getDecorations().entrySet()
                .forEach(e -> json.add(e.getKey().getInternalName(), new JsonPrimitive(e.getValue().toString())));

        // Colour
        if (src.getColour() != TextColour.NONE) {
            json.add("color", new JsonPrimitive(src.getColour().getInternalName()));
        }

        // Insertion
        if (src.getInsertion().isPresent()) {
            json.add("insertion", new JsonPrimitive(src.getInsertion().get()));
        }

        // Children
        if (src.hasChildren()) {
            final JsonArray extra = new JsonArray();

            // Serialise all children
            src.getChildren().stream()
                    .map(text -> this.serialize(text, text.getClass(), context))
                    .forEach(extra::add);

            json.add("extra", extra);
        }

        return json;
    }

}
