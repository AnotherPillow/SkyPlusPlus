package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Chat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class ToolSaver {

    public static ActionResult checkHand(PlayerEntity player, World world, Hand hand) {
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        ItemStack main = player.getStackInHand(hand);
        if (main.getMaxDamage() < 1) return ActionResult.PASS;

        int remainingDurability = main.getMaxDamage() - main.getDamage();

//        Chat.send(remainingDurability + "<- current, threshold: " + config.toolSaverDurabilityLimit);
        if (config.toolSaverDurabilityLimit >= remainingDurability) {
            // i hate this so much
            Chat.send(Chat.addLogo((Text.of("Saved your: ").getWithStyle(Style.EMPTY.withColor(0x707070))).get(0).copy().append(main.getName())));
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }
}
