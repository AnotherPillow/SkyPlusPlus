package com.anotherpillow.skyplusplus.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

import com.anotherpillow.skyplusplus.util.Chat;
import net.minecraft.text.Text;


public class ConverterCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();
        //register /sconvert <st, sc, sh, dc, stack, single, dub, double, shulk> <amount (int)>
        String[] validTypes = {"st","stacks","stack","sc","SC","single","singlechest","chest","sh","shulk","shulker","shulkerbox","box","dc","DC","doublechest","dub","double"};

        dispatcher.register(ClientCommandManager.literal("sconvert")
                .then(ClientCommandManager.argument("type", StringArgumentType.string())
                .then(ClientCommandManager.argument("amount", IntegerArgumentType.integer()).executes(context -> {
                    //get the arguments
                    String type = StringArgumentType.getString(context, "type");
                    int amount = IntegerArgumentType.getInteger(context, "amount");

                    //respond to the command

                    FabricClientCommandSource src = context.getSource();

                    //check if type is valid
                    boolean valid = false;
                    for (String validType : validTypes) {
                        if (type.equals(validType)) {
                            valid = true;
                            break;
                        }
                    }
                    if (!valid) {
                        src.sendFeedback(Text.of(Chat.addLogo("Invalid type! Valid types are: st, sc, sh, dc, stack, single, dub, double, shulk")));
                        return 0;
                    }

                    int wholes = 0;
                    int remainder = 0;

                    switch (type) {
                        case "st":
                        case "stacks":
                        case "stack":
                            if (!"stack".equals(type))
                                type = "stack";

                            wholes = amount / 64;
                            remainder = amount % 64;
                            break;
                        case "sc":
                        case "SC":
                        case "single":
                        case "singlechest":
                        case "chest":
                        case "sh":
                        case "shulk":
                        case "shulker":
                        case "shulkerbox":
                        case "box":
                            if (!"SC".equals(type))
                                type = "SC";
                            wholes = amount / 1728;
                            remainder = amount % 1728;
                            break;
                        case "dc":
                        case "DC":
                        case "doublechest":
                        case "dub":
                        case "double":
                            if (!"DC".equals(type))
                                type = "DC";
                            wholes = amount / 2304;
                            remainder = amount % 2304;
                            break;
                    }

                    //respond to the command
                    src.sendFeedback(Text.of(Chat.addLogo(amount + " items is " + wholes + " " + type +  " and " + remainder + " items")));



                    //src.sendFeedback(Text.of(ChatLogo.logo + "Converting " + amount + " " + type + "s..."));

                    return 1;
                }))));


    }
}
