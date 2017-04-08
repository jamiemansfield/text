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

import static me.jamiemansfield.mc.text.serialiser.TextSerialiser.Registry.CLICK_EVENT_ACTION;
import static me.jamiemansfield.mc.text.serialiser.TextSerialiser.Registry.COLOUR;
import static me.jamiemansfield.mc.text.serialiser.TextSerialiser.Registry.DECORATION;
import static me.jamiemansfield.mc.text.serialiser.TextSerialiser.Registry.HOVER_EVENT_ACTION;

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
import com.google.gson.JsonSyntaxException;
import me.jamiemansfield.mc.text.LiteralText;
import me.jamiemansfield.mc.text.Text;
import me.jamiemansfield.mc.text.TranslatableText;
import me.jamiemansfield.mc.text.event.ClickEvent;
import me.jamiemansfield.mc.text.event.HoverEvent;
import me.jamiemansfield.mc.text.format.TextColour;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * The {@link TextSerialiser} for Mojang's json format.
 */
public final class JsonSerialiser extends TextSerialiser implements JsonSerializer<Text>, JsonDeserializer<Text> {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(Text.class, new JsonSerialiser())
            .create();

    JsonSerialiser() {
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

        DECORATION.getEntries().stream()
                .filter(decoration -> obj.has(decoration.getInternalName()))
                .forEach(decoration -> text.apply(decoration, obj.get(decoration.getInternalName()).getAsBoolean()));

        if (obj.has("color")) {
            final Optional<TextColour> colour = COLOUR.getEntries().stream()
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

        if (obj.has("clickEvent")) {
            final Optional<ClickEvent.Action> action = CLICK_EVENT_ACTION.getEntries().stream()
                    .filter(a -> obj.get("clickEvent").getAsJsonObject().get("action").getAsString().equals(a.getInternalName()))
                    .findAny();
            if (!action.isPresent()) {
                throw new JsonParseException("Terribly sorry, but I will not be able to deserialise " + json);
            }
            final JsonElement element = obj.get("clickEvent").getAsJsonObject().get("value");
            text.click(new ClickEvent(action.get(), this.deserialize(element, element.getClass(), context)));
        }

        if (obj.has("hoverEvent")) {
            final Optional<HoverEvent.Action> action = HOVER_EVENT_ACTION.getEntries().stream()
                    .filter(a -> obj.get("hoverEvent").getAsJsonObject().get("action").getAsString().equals(a.getInternalName()))
                    .findAny();
            if (!action.isPresent()) {
                throw new JsonParseException("Terribly sorry, but I will not be able to deserialise " + json);
            }
            final JsonElement element = obj.get("hoverEvent").getAsJsonObject().get("value");
            text.hover(new HoverEvent(action.get(), this.deserialize(element, element.getClass(), context)));
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

        // Click event
        if (src.getClickEvent().isPresent()) {
            final JsonObject clickEvent = new JsonObject();
            clickEvent.add("action", new JsonPrimitive(src.getClickEvent().get().getAction().getInternalName()));
            clickEvent.add("value",
                    this.serialize(src.getClickEvent().get().getValue(), src.getClickEvent().get().getValue().getClass(), context));

            json.add("clickEvent", clickEvent);
        }

        // Hover event
        if (src.getHoverEvent().isPresent()) {
            final JsonObject hoverEvent = new JsonObject();
            hoverEvent.add("action", new JsonPrimitive(src.getHoverEvent().get().getAction().getInternalName()));
            hoverEvent.add("value",
                    this.serialize(src.getHoverEvent().get().getValue(), src.getHoverEvent().get().getValue().getClass(), context));

            json.add("hoverEvent", hoverEvent);
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

    @Override
    public String serialise(final Text obj) {
        return GSON.toJson(obj);
    }

    @Override
    public Text deserialise(final String obj) throws TextParseException {
        try {
            return GSON.fromJson(obj, Text.class);
        } catch (JsonSyntaxException ex) {
            throw new TextParseException("Failed to parse json text!", ex);
        }
    }

}
