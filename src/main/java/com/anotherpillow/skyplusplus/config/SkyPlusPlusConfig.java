package com.anotherpillow.skyplusplus.config;

import com.anotherpillow.skyplusplus.util.TimeConsts;
import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import dev.isxander.yacl.config.ConfigInstance;
import dev.isxander.yacl.config.GsonConfigInstance;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import dev.isxander.yacl.gui.controllers.string.StringController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class SkyPlusPlusConfig {
    public static GsonConfigInstance<SkyPlusPlusConfig> configInstance = new GsonConfigInstance<>(SkyPlusPlusConfig.class, FabricLoader.getInstance().getConfigDir().resolve("skyplusplus.json"));

    @ConfigEntry public int traderX = 16;
    @ConfigEntry public int traderY = 16;
    @ConfigEntry public boolean enableTraderFinder = true;
    @ConfigEntry public boolean enableTraderTitles = true;

    @ConfigEntry public boolean hideVisitingMessages = false;
    @ConfigEntry public boolean hideSkyblockMessages = false;
    @ConfigEntry public boolean hideUnscramblingMessages = false;
    @ConfigEntry public boolean hideAFKMessages = false;
    @ConfigEntry public boolean hideNewUserMessages = false;
    @ConfigEntry public boolean hideDeathsMessages = false;
    @ConfigEntry public boolean hideRaffleMessages = false;

    @ConfigEntry public boolean enableAutoResponder = false;
    @ConfigEntry public String autoResponderMessage = "I am currently AFK. Please message me on Discord instead at Username#0000.";
    @ConfigEntry public boolean hideAdvancementMessages = true;
    @ConfigEntry public boolean hideBroadcastMessages = true;
    @ConfigEntry public boolean hideExpandMessages = true;
    @ConfigEntry public boolean hideLuckyCratesMessages = true;
    @ConfigEntry public boolean hideMailMessages = true;
    @ConfigEntry public boolean hideVoteMessages = false;
    @ConfigEntry public boolean removeChatRanks = false;

    @ConfigEntry public boolean chatPrefix = false;
    @ConfigEntry public String chatPrefixMessage = "&b";
    @ConfigEntry public boolean chatSuffix = false;
    @ConfigEntry public String chatSuffixMessage = "!";

    @ConfigEntry public boolean betterChangeBiomeEnabled = true;
    @ConfigEntry public boolean betterCrateKeysEnabled = true;
    @ConfigEntry public boolean showEmptyShopsEnabled = true;
    @ConfigEntry public boolean dynamicScoreboardTitle = true;


    @ConfigEntry public boolean extraTabEnabled = true;
    @ConfigEntry public boolean preventHeadDropping = true;
    @ConfigEntry public boolean autoRaffleEnabled = false;
    @ConfigEntry public boolean antiPCEnabled = false;
    @ConfigEntry public boolean antiGrassPlace = false;
    @ConfigEntry public boolean lowerCommandsEnabled = true;
    @ConfigEntry public String joinCommandsList = "";

    @ConfigEntry public boolean enableDiscordRPC = true;

    @ConfigEntry public boolean enableEconomyAdverts = false;
    @ConfigEntry public boolean enableSurvivalAdverts = false;
    @ConfigEntry public int economyAdInterval = TimeConsts.Minute * 10;
    @ConfigEntry public int survivalAdInterval = TimeConsts.Minute * 5;
    @ConfigEntry public String economyAdMessage = "Come visit my very cool economy island!";
    @ConfigEntry public String survivalAdMessage = "Come visit my very cool skyblock island!";


    public static Screen getConfigScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(configInstance, ((defaults, config, builder) -> builder
                .title(Text.literal("Sky++ Config"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Trader Notifications"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable"))
                                .tooltip(Text.literal("Enable Wandering Trader Notifications"))
                                .binding(defaults.enableTraderFinder, () -> config.enableTraderFinder, v -> config.enableTraderFinder = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Title"))
                                .tooltip(Text.literal("Shows a title when a trader is at spawn."))
                                .binding(defaults.enableTraderTitles, () -> config.enableTraderTitles, v -> config.enableTraderTitles = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.literal("Notification X"))
                                .tooltip(Text.literal("X"))
                                .binding(defaults.traderX, () -> config.traderX, v -> config.traderX = v)
                                .controller(opt -> new IntegerSliderController(opt, 0, 4000, 16))
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.literal("Notification Y"))
                                .tooltip(Text.literal("Y"))
                                .binding(defaults.traderY, () -> config.traderY, v -> config.traderY = v)
                                .controller(opt -> new IntegerSliderController(opt, 0, 4000, 16))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Filter Chat"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Visiting Messages"))
                                .tooltip(Text.literal("Hide messages when you visit other islands"))
                                .binding(defaults.hideVisitingMessages, () -> config.hideVisitingMessages, v -> config.hideVisitingMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Skyblock Messages"))
                                .tooltip(Text.literal("Hide [Skyblock] messages"))
                                .binding(defaults.hideSkyblockMessages, () -> config.hideSkyblockMessages, v -> config.hideSkyblockMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Unscrambling Messages"))
                                .tooltip(Text.literal("Hide Hover for word/letter unscrambling messages"))
                                .binding(defaults.hideUnscramblingMessages, () -> config.hideUnscramblingMessages, v -> config.hideUnscramblingMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide AFK Messages"))
                                .tooltip(Text.literal("Hide staff going AFK messages"))
                                .binding(defaults.hideAFKMessages, () -> config.hideAFKMessages, v -> config.hideAFKMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide New User Messages"))
                                .tooltip(Text.literal("Hide new user messages"))
                                .binding(defaults.hideNewUserMessages, () -> config.hideNewUserMessages, v -> config.hideNewUserMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Death Count Messages"))
                                .tooltip(Text.literal("Hide daily death count messages"))
                                .binding(defaults.hideDeathsMessages, () -> config.hideDeathsMessages, v -> config.hideDeathsMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Raffle Messages"))
                                .tooltip(Text.literal("Hide Raffle (lottery) messages"))
                                .binding(defaults.hideRaffleMessages, () -> config.hideRaffleMessages, v -> config.hideRaffleMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Advancement Messages"))
                                .tooltip(Text.literal("Hide advancement messages"))
                                .binding(defaults.hideAdvancementMessages, () -> config.hideAdvancementMessages, v -> config.hideAdvancementMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Broadcast Messages"))
                                .tooltip(Text.literal("Hide Broadcast (votes, etc) messages"))
                                .binding(defaults.hideBroadcastMessages, () -> config.hideBroadcastMessages, v -> config.hideBroadcastMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Island Expansion Messages"))
                                .tooltip(Text.literal("Hide \"You must expand your island to access this area ...\" messages"))
                                .binding(defaults.hideExpandMessages, () -> config.hideExpandMessages, v -> config.hideExpandMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Lucky Crates Messages"))
                                .tooltip(Text.literal("Hide Lucky Crates messages"))
                                .binding(defaults.hideLuckyCratesMessages, () -> config.hideLuckyCratesMessages, v -> config.hideLuckyCratesMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Mail Messages"))
                                .tooltip(Text.literal("Hide \"You have X messages! ...\" messages"))
                                .binding(defaults.hideMailMessages, () -> config.hideMailMessages, v -> config.hideMailMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Hide Vote Messages"))
                                .tooltip(Text.literal("Hide messages notifying you of someone else's votes."))
                                .binding(defaults.hideVoteMessages, () -> config.hideVoteMessages, v -> config.hideVoteMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Remove chat ranks"))
                                .tooltip(Text.literal("Remove the [Rank Prefix] from chat messages."))
                                .binding(defaults.removeChatRanks, () -> config.removeChatRanks, v -> config.removeChatRanks = v)
                                .controller(TickBoxController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Auto Responder"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable"))
                                .tooltip(Text.literal("Enable Auto Responder"))
                                .binding(defaults.enableAutoResponder, () -> config.enableAutoResponder, v -> config.enableAutoResponder = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.literal("Message"))
                                .tooltip(Text.literal("Message to respond with"))
                                .binding(defaults.autoResponderMessage, () -> config.autoResponderMessage, v -> config.autoResponderMessage = v)
                                .controller(StringController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Message Prefix/Suffix"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Prefix"))
                                .tooltip(Text.literal("Enable message prefixing"))
                                .binding(defaults.chatPrefix, () -> config.chatPrefix, v -> config.chatPrefix = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Suffix"))
                                .tooltip(Text.literal("Message message suffixing"))
                                .binding(defaults.chatSuffix, () -> config.chatSuffix, v -> config.chatSuffix = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.literal("Prefix"))
                                .tooltip(Text.literal("Prefix to add to messages"))
                                .binding(defaults.chatPrefixMessage, () -> config.chatPrefixMessage, v -> config.chatPrefixMessage = v)
                                .controller(StringController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.literal("Suffix"))
                                .tooltip(Text.literal("Suffix to add to messages"))
                                .binding(defaults.chatSuffixMessage, () -> config.chatSuffixMessage, v -> config.chatSuffixMessage = v)
                                .controller(StringController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Better Items/UIs"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Better Changebiome UI"))
                                .tooltip(Text.literal("Enable a better changebiome UI (may require a restart!)"))
                                .binding(defaults.betterChangeBiomeEnabled, () -> config.betterChangeBiomeEnabled, v -> config.betterChangeBiomeEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Better Crate Keys"))
                                .tooltip(Text.literal("Enable coloured crate keys (may require a restart!)"))
                                .binding(defaults.betterCrateKeysEnabled, () -> config.betterCrateKeysEnabled, v -> config.betterCrateKeysEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Show Empty Shops"))
                                .tooltip(Text.literal("Enable showing barriers on the shops screen when they have no stock (may require a restart!)"))
                                .binding(defaults.showEmptyShopsEnabled, () -> config.showEmptyShopsEnabled, v -> config.showEmptyShopsEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Dynamic Scoreboard Title"))
                                .tooltip(Text.literal("Change scoreboard title based on connected subserver."))
                                .binding(defaults.dynamicScoreboardTitle, () -> config.dynamicScoreboardTitle, v -> config.dynamicScoreboardTitle = v)
                                .controller(TickBoxController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Tweaks/Improvements"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("ExtraTab"))
                                .tooltip(Text.literal("Allows for extra columns in tab."))
                                .binding(defaults.extraTabEnabled, () -> config.extraTabEnabled, v -> config.extraTabEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Prevent Head Dropping"))
                                .tooltip(Text.literal("Prevents you from dropping heads."))
                                .binding(defaults.preventHeadDropping, () -> config.preventHeadDropping, v -> config.preventHeadDropping = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Prevent Grass Placing"))
                                .tooltip(Text.literal("Prevents you from placing grass."))
                                .binding(defaults.antiGrassPlace, () -> config.antiGrassPlace, v -> config.antiGrassPlace = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("AutoRaffle"))
                                .tooltip(Text.literal("Automatically buys 5 raffle tickets each round."))
                                .binding(defaults.autoRaffleEnabled, () -> config.autoRaffleEnabled, v -> config.autoRaffleEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("AntiPC"))
                                .tooltip(Text.literal("Also known as AntiBookBan. Prevents large items from kicking or banning you (CLIENT ONLY)"))
                                .binding(defaults.antiPCEnabled, () -> config.antiPCEnabled, v -> config.antiPCEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Automatic Lowercase Commands"))
                                .tooltip(Text.literal("Automatically converts commands to lowercase."))
                                .binding(defaults.lowerCommandsEnabled, () -> config.lowerCommandsEnabled, v -> config.lowerCommandsEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.literal("Join Commands"))
                                .tooltip(Text.literal("Commands to run upon joining the server. Separate with ,. Leave empty for none."))
                                .binding(defaults.joinCommandsList, () -> config.joinCommandsList, v -> config.joinCommandsList = v)
                                .controller(StringController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Discord RPC"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Discord RPC"))
                                .tooltip(Text.literal("Enables Sky++'s Discord RPC (requires restart after changing)."))
                                .binding(defaults.enableDiscordRPC, () -> config.enableDiscordRPC, v -> config.enableDiscordRPC = v)
                                .controller(TickBoxController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Auto Advertisement"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Skyblock Economy Adverts"))
                                .tooltip(Text.literal("Enables auto adverts on the /economy server (requires relog)."))
                                .binding(defaults.enableEconomyAdverts, () -> config.enableEconomyAdverts, v -> config.enableEconomyAdverts = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Enable Skyblock Survival Adverts"))
                                .tooltip(Text.literal("Enables auto adverts on the /skyblock server (requires relog)."))
                                .binding(defaults.enableSurvivalAdverts, () -> config.enableSurvivalAdverts, v -> config.enableSurvivalAdverts = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.literal("Economy Advertisement"))
                                .tooltip(Text.literal("Message to send on economy."))
                                .binding(defaults.economyAdMessage, () -> config.economyAdMessage, v -> config.economyAdMessage = v)
                                .controller(StringController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.literal("Survival Advertisement"))
                                .tooltip(Text.literal("Message to send on survival."))
                                .binding(defaults.survivalAdMessage, () -> config.survivalAdMessage, v -> config.survivalAdMessage = v)
                                .controller(StringController::new)
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.literal("Economy Ad Interval (Seconds)"))
                                .tooltip(Text.literal("Time between ads on /economy in seconds."))
                                .binding(defaults.economyAdInterval, () -> config.economyAdInterval, v -> config.economyAdInterval = v)
                                .controller(opt -> new IntegerSliderController(opt, TimeConsts.MINIMUM_ECONOMY / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second, TimeConsts.Minute / TimeConsts.Second))
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.literal("Survival Ad Interval (Seconds)"))
                                .tooltip(Text.literal("Time between ads on /skyblock in seconds."))
                                .binding(defaults.survivalAdInterval, () -> config.survivalAdInterval, v -> config.survivalAdInterval = v)
                                .controller(opt -> new IntegerSliderController(opt, TimeConsts.MINIMUM_SKYBLOCK / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second, TimeConsts.Minute / TimeConsts.Second))
                                .build())
                        .build())
        )).generateScreen(parentScreen);
    }
}
