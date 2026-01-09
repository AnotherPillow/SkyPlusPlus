package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.SkyPlusPlus;
import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import net.minecraft.text.Text;
//? if >=1.20.4 {
import net.minecraft.text.TextCodecs;
//?} else {
//?}
import org.apache.logging.log4j.Level;

public class RemoveChatRanks {

    public static Text process(Text original) {
        //? if >=1.20.4 {
        String json = TextCodecs.CODEC.encodeStart(JsonOps.INSTANCE, original).result().toString();
        //?} else {
        /*String json = Text.Serializer.toJson(original);
         *///?}

        JsonElement jsonElement = JsonParser.parseString(json);

        if (jsonElement.isJsonObject()) {
            JsonObject obj = jsonElement.getAsJsonObject();
            JsonArray extras = obj.getAsJsonArray("extra");

            if (extras != null && extras.size() <= 2) {
                JsonObject firstExtra = extras.get(0).getAsJsonObject();
                String oldText = firstExtra.get("text").getAsString();
                if (!oldText.contains("[")) return original;
                String[] parts = oldText.split("]");
                firstExtra.addProperty("text", String.join("", java.util.Arrays.copyOfRange(parts, 1, parts.length)).replaceFirst("^(§[0-9a-fl-okrx]) ", "$1").trim() + ' ');
            }
        }

        Gson gson = new Gson();

        //? if >=1.21 {
        return TextCodecs.CODEC.parse(JsonOps.INSTANCE, jsonElement).getOrThrow();
        //?} else if >=1.20.4 {
        /*return TextCodecs.CODEC.parse(JsonOps.INSTANCE, jsonElement).get().orThrow();
        *///?} else {
        /*String newJson = gson.toJson(jsonElement);
        return Text.Serializer.fromJson(newJson);
         *///?}

    }
}
