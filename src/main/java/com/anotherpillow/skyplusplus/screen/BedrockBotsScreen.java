package com.anotherpillow.skyplusplus.screen;


import com.anotherpillow.skyplusplus.util.ChatLogo;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BedrockBotsScreen extends LightweightGuiDescription {
    public BedrockBotsScreen() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
//        root.setSize(18/*px*/ * 16, 18/*px*/ * 12);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
        root.add(icon, 1, 2, 1, 1);


        WTextField text = new WTextField(Text.of("Gamertag"));
        root.add(text, 6, 4, 4, 1);

        WButton createButton = new WButton(Text.of("Create Bot!"));
        root.add(createButton, 6, 8, 4, 1);

        WButton cancelButton = new WButton(Text.of("Cancel!"));
        root.add(cancelButton, 6, 10, 4, 1);

        cancelButton.setOnClick(() -> {
            MinecraftClient.getInstance().setScreen(null);
        });
        createButton.setOnClick(() -> {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(ChatLogo.addLogo("You clicked the cool button!")));
            MinecraftClient.getInstance().setScreen(null);
        });

        root.validate(this);
    }
}