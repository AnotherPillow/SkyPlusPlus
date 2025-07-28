package com.anotherpillow.skyplusplus.util;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Optional;

public class TextStringifier {
    public static Text prefixButton(Text main) {
        String stringified = TextStringifier.from(main);
        Text prefixedWithStringifier = Text.empty().append(Text.literal("<c>").setStyle(
                Style.EMPTY
                        .withColor(Formatting.GRAY)
                        .withHoverEvent(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        Text.literal(stringified)
                                )
                        )
                        .withClickEvent(
                                new ClickEvent(
                                        ClickEvent.Action.COPY_TO_CLIPBOARD,
                                        stringified
                                )
                        )
        )).append(main);

        return prefixedWithStringifier;
    }

    public static String from(Text text) {
        StringBuilder out = new StringBuilder();
        MutableObject<Style> last = new MutableObject<>(Style.EMPTY);

        text.visit((style, string) -> {
            if (!style.equals(last.getValue())) {
                out.append("&r");
                last.setValue(style);

                // colour
                if (style.getColor() != null) {
                    TextColor c = style.getColor();
                    if (c.getName().startsWith("#")) {
                        String hex = c.getName().substring(1).toUpperCase();
                        out.append("&x");
                        for (char ch : hex.toCharArray()) out.append('&').append(ch);
                    } else {
                        Formatting f = Formatting.byName(c.getName());
                        if (f != null) out.append('&').append(f.getCode());
                    }
                }

                // styles
                if (style.isObfuscated())    out.append("&k");
                if (style.isBold())          out.append("&l");
                if (style.isStrikethrough()) out.append("&m");
                if (style.isUnderlined())    out.append("&n");
                if (style.isItalic())        out.append("&o");
            }

            out.append(string);
            return Optional.empty();
        }, Style.EMPTY);

        return out.toString().replace("§", "&");
    }
}
