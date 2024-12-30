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
    @ConfigEntry public boolean hideVisitingTitle = false;
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
                .title(Text.translatable("skyplusplus.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.trader-notifications"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.trader-enable"))
                                .tooltip(Text.translatable("skyplusplus.config.trader-enable-desc"))
                                .binding(defaults.enableTraderFinder, () -> config.enableTraderFinder, v -> config.enableTraderFinder = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.trader-enable-title"))
                                .tooltip(Text.translatable("skyplusplus.config.trader-enable-title-desc"))
                                .binding(defaults.enableTraderTitles, () -> config.enableTraderTitles, v -> config.enableTraderTitles = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.trader-notif-x"))
                                .tooltip(Text.translatable("skyplusplus.config.trader-notif-x-desc"))
                                .binding(defaults.traderX, () -> config.traderX, v -> config.traderX = v)
                                .controller(opt -> new IntegerSliderController(opt, 0, 4000, 16))
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.trader-notif-y"))
                                .tooltip(Text.translatable("skyplusplus.config.trader-notif-y-desc"))
                                .binding(defaults.traderY, () -> config.traderY, v -> config.traderY = v)
                                .controller(opt -> new IntegerSliderController(opt, 0, 4000, 16))
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.chatfilter.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidevisitmsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidevisitmsgs-desc"))
                                .binding(defaults.hideVisitingMessages, () -> config.hideVisitingMessages, v -> config.hideVisitingMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidevisittitles"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidevisittitles-desc"))
                                .binding(defaults.hideVisitingTitle, () -> config.hideVisitingTitle, v -> config.hideVisitingTitle = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidesbmsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidesbmsgs-desc"))
                                .binding(defaults.hideSkyblockMessages, () -> config.hideSkyblockMessages, v -> config.hideSkyblockMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideunscramble"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hideunscramble-desc"))
                                .binding(defaults.hideUnscramblingMessages, () -> config.hideUnscramblingMessages, v -> config.hideUnscramblingMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideafk"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hideafk-desc"))
                                .binding(defaults.hideAFKMessages, () -> config.hideAFKMessages, v -> config.hideAFKMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidenewusermsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidenewusermsgs-desc"))
                                .binding(defaults.hideNewUserMessages, () -> config.hideNewUserMessages, v -> config.hideNewUserMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs-desc"))
                                .binding(defaults.hideDeathsMessages, () -> config.hideDeathsMessages, v -> config.hideDeathsMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hiderafflemsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hiderafflemsgs-desc"))
                                .binding(defaults.hideRaffleMessages, () -> config.hideRaffleMessages, v -> config.hideRaffleMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideadvancementmsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs-desc"))
                                .binding(defaults.hideAdvancementMessages, () -> config.hideAdvancementMessages, v -> config.hideAdvancementMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidebroadcastmsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidebroadcastmsgs-desc"))
                                .binding(defaults.hideBroadcastMessages, () -> config.hideBroadcastMessages, v -> config.hideBroadcastMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideislandexpansionmsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hideislandexpansionmsgs-desc"))
                                .binding(defaults.hideExpandMessages, () -> config.hideExpandMessages, v -> config.hideExpandMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideluckycratemsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hideluckycratemsgs-desc"))
                                .binding(defaults.hideLuckyCratesMessages, () -> config.hideLuckyCratesMessages, v -> config.hideLuckyCratesMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidemailmsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidemailmsgs-desc"))
                                .binding(defaults.hideMailMessages, () -> config.hideMailMessages, v -> config.hideMailMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidevotemsgs"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.hidevotemsgs-desc"))
                                .binding(defaults.hideVoteMessages, () -> config.hideVoteMessages, v -> config.hideVoteMessages = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.removechatranks"))
                                .tooltip(Text.translatable("skyplusplus.config.chatfilter.removechatranks-desc"))
                                .binding(defaults.removeChatRanks, () -> config.removeChatRanks, v -> config.removeChatRanks = v)
                                .controller(TickBoxController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.autoresponder.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.autoresponder.enable"))
                                .tooltip(Text.translatable("skyplusplus.config.autoresponder.enable-desc"))
                                .binding(defaults.enableAutoResponder, () -> config.enableAutoResponder, v -> config.enableAutoResponder = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.autoresponder.message-desc"))
                                .tooltip(Text.translatable("skyplusplus.config.autoresponder.message"))
                                .binding(defaults.autoResponderMessage, () -> config.autoResponderMessage, v -> config.autoResponderMessage = v)
                                .controller(StringController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.message-prefix-suffix.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix"))
                                .tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-desc"))
                                .binding(defaults.chatPrefix, () -> config.chatPrefix, v -> config.chatPrefix = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix"))
                                .tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-desc"))
                                .binding(defaults.chatSuffix, () -> config.chatSuffix, v -> config.chatSuffix = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-message"))
                                .tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-message-desc"))
                                .binding(defaults.chatPrefixMessage, () -> config.chatPrefixMessage, v -> config.chatPrefixMessage = v)
                                .controller(StringController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-message"))
                                .tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-message-desc"))
                                .binding(defaults.chatSuffixMessage, () -> config.chatSuffixMessage, v -> config.chatSuffixMessage = v)
                                .controller(StringController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.better-items-uis.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.changebiome"))
                                .tooltip(Text.translatable("skyplusplus.config.better-items-uis.changebiome-desc"))
                                .binding(defaults.betterChangeBiomeEnabled, () -> config.betterChangeBiomeEnabled, v -> config.betterChangeBiomeEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.better-crate-keys"))
                                .tooltip(Text.translatable("skyplusplus.config.better-items-uis.better-crate-keys-desc"))
                                .binding(defaults.betterCrateKeysEnabled, () -> config.betterCrateKeysEnabled, v -> config.betterCrateKeysEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.show-empty-shops"))
                                .tooltip(Text.translatable("skyplusplus.config.better-items-uis.show-empty-shops-desc"))
                                .binding(defaults.showEmptyShopsEnabled, () -> config.showEmptyShopsEnabled, v -> config.showEmptyShopsEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.dynamic-scoreboard-title"))
                                .tooltip(Text.translatable("skyplusplus.config.better-items-uis.dynamic-scoreboard-title-desc"))
                                .binding(defaults.dynamicScoreboardTitle, () -> config.dynamicScoreboardTitle, v -> config.dynamicScoreboardTitle = v)
                                .controller(TickBoxController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.tweaks-improvements.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.extratab"))
                                .tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.extratab-desc"))
                                .binding(defaults.extraTabEnabled, () -> config.extraTabEnabled, v -> config.extraTabEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-head-dropping"))
                                .tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-head-dropping-desc"))
                                .binding(defaults.preventHeadDropping, () -> config.preventHeadDropping, v -> config.preventHeadDropping = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-grass-placing"))
                                .tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-grass-placing-desc"))
                                .binding(defaults.antiGrassPlace, () -> config.antiGrassPlace, v -> config.antiGrassPlace = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.autoraffle"))
                                .tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.autoraffle-desc"))
                                .binding(defaults.autoRaffleEnabled, () -> config.autoRaffleEnabled, v -> config.autoRaffleEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.antipc"))
                                .tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.antipc-desc"))
                                .binding(defaults.antiPCEnabled, () -> config.antiPCEnabled, v -> config.antiPCEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.automatic-lowercase-commands"))
                                .tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.automatic-lowercase-commands-desc"))
                                .binding(defaults.lowerCommandsEnabled, () -> config.lowerCommandsEnabled, v -> config.lowerCommandsEnabled = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.join-commands"))
                                .tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.join-commands-desc"))
                                .binding(defaults.joinCommandsList, () -> config.joinCommandsList, v -> config.joinCommandsList = v)
                                .controller(StringController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.discord-rpc.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.discord-rpc.enable"))
                                .tooltip(Text.translatable("skyplusplus.config.discord-rpc.enable-desc"))
                                .binding(defaults.enableDiscordRPC, () -> config.enableDiscordRPC, v -> config.enableDiscordRPC = v)
                                .controller(TickBoxController::new)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.auto-advertisements.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.economy-enable"))
                                .tooltip(Text.translatable("skyplusplus.config.auto-advertisements.economy-enable-desc"))
                                .binding(defaults.enableEconomyAdverts, () -> config.enableEconomyAdverts, v -> config.enableEconomyAdverts = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.survival-enable"))
                                .tooltip(Text.translatable("skyplusplus.config.auto-advertisements.survival-enable-desc"))
                                .binding(defaults.enableSurvivalAdverts, () -> config.enableSurvivalAdverts, v -> config.enableSurvivalAdverts = v)
                                .controller(TickBoxController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.economy-advertisement"))
                                .tooltip(Text.translatable("skyplusplus.config.auto-advertisements.economy-advertisement-desc"))
                                .binding(defaults.economyAdMessage, () -> config.economyAdMessage, v -> config.economyAdMessage = v)
                                .controller(StringController::new)
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.survival-advertisement"))
                                .tooltip(Text.translatable("skyplusplus.config.auto-advertisements.survival-advertisement-desc"))
                                .binding(defaults.survivalAdMessage, () -> config.survivalAdMessage, v -> config.survivalAdMessage = v)
                                .controller(StringController::new)
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.economy-interval"))
                                .tooltip(Text.translatable("skyplusplus.config.auto-advertisements.economy-interval-desc"))
                                .binding(defaults.economyAdInterval, () -> config.economyAdInterval, v -> config.economyAdInterval = v)
                                .controller(opt -> new IntegerSliderController(opt, TimeConsts.MINIMUM_ECONOMY / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second, TimeConsts.Minute / TimeConsts.Second))
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.survival-interval"))
                                .tooltip(Text.translatable("skyplusplus.config.auto-advertisements.survival-interval-desc"))
                                .binding(defaults.survivalAdInterval, () -> config.survivalAdInterval, v -> config.survivalAdInterval = v)
                                .controller(opt -> new IntegerSliderController(opt, TimeConsts.MINIMUM_SKYBLOCK / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second, TimeConsts.Minute / TimeConsts.Second))
                                .build())
                        .build())
        )).generateScreen(parentScreen);
    }
}
