package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin {
    @Unique
    private static final Identifier skyplusplus$OLD_TEXTURE = new Identifier("textures/gui/container/generic_54.png");

    @Redirect(
            method = "drawBackground(Lnet/minecraft/client/util/math/MatrixStack;FII)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
                    ordinal = 0
            )
    )
    private void replaceSetShaderTexture(int sampler, Identifier texture) {
        // this was so painful omg
        Text title = ((ScreenAccessor) this).skyplusplus$getTitle();
        String stitle = title.getString().trim();
        SkyPlusPlusConfig config = SkyPlusPlusClient.config;

        if ("Top Skyblock Islands | Ads".equals(stitle) && config.cleanAdGuiEnabled) {
            RenderSystem.setShaderTexture(sampler, SkyPlusPlusClient.cleanAdBackground);
        } else if ("Profile | Ads".equals(stitle) && config.cleanAdGuiEnabled) {
            RenderSystem.setShaderTexture(sampler, SkyPlusPlusClient.cleanAdProfileBackground);
        } else if ("Daily Quests".equals(stitle) && config.cleanQuestGuiEnabled) {
            RenderSystem.setShaderTexture(sampler, SkyPlusPlusClient.cleanQuestsMainBackground);
        } else if ("Quest Points Shop".equals(stitle) && config.cleanQuestGuiEnabled) {
            RenderSystem.setShaderTexture(sampler, SkyPlusPlusClient.cleanQuestsShopBackground);
        } else {
            RenderSystem.setShaderTexture(sampler, skyplusplus$OLD_TEXTURE);
        }

    }
}
