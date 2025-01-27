package com.anotherpillow.skyplusplus.screen;


import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WanderingTraderScreen extends LightweightGuiDescription {
    public static boolean shouldRender = false;
    public static WGridPanel generateRoot() {
        WGridPanel root = new WGridPanel();
//        root.setSize(18/*px*/ * 16, 18/*px*/ * 12);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        root.setBackgroundPainter(BackgroundPainter.VANILLA);

        WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
        root.add(icon, 1, 2, 1, 1);

                // https://github.com/CottonMC/LibGui/wiki/Getting-Started-with-GUIs

//        WItemSlot = new WItemSlot()

        return root;
    }

    public WanderingTraderScreen() {
        WGridPanel root = generateRoot();
        setRootPanel(root);
        root.validate(this);
    }
}