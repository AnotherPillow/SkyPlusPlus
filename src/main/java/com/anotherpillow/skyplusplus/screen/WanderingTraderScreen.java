package com.anotherpillow.skyplusplus.screen;


import com.anotherpillow.skyplusplus.util.Chat;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WanderingTraderScreen extends LightweightGuiDescription {
    public static boolean shouldRender = false;
    public static WGridPanel generateRoot() {
        WGridPanel root = new WGridPanel();
//        root.setSize(18/*px*/ * 16, 18/*px*/ * 12);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        root.setBackgroundPainter(new BackgroundPainter() {
            @Override
            public void paintBackground(MatrixStack matrices, int left, int top, WWidget panel) {
                BackgroundPainter.VANILLA.paintBackground(matrices, left, top, panel);
            }
        });

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
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(Chat.addLogo("You clicked the cool button!")));
            MinecraftClient.getInstance().setScreen(null);
        });

        return root;
    }

    public WanderingTraderScreen() {
        WGridPanel root = generateRoot();
        setRootPanel(root);
        root.validate(this);
    }
}