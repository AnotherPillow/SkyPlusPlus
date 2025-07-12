package com.anotherpillow.skyplusplus.config;

//? if >1.19.2 {
import com.anotherpillow.skyplusplus.util.TimeConsts;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.gui.controllers.TickBoxController;
import dev.isxander.yacl3.config.ConfigInstance;
import dev.isxander.yacl3.config.GsonConfigInstance;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import dev.isxander.yacl3.gui.controllers.string.StringController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.fabricmc.loader.api.FabricLoader;
//? } else {
/*import com.anotherpillow.skyplusplus.util.TimeConsts;
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
*///?}


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

    @ConfigEntry public boolean hideAdvancementMessages = true;
    @ConfigEntry public boolean hideBroadcastMessages = true;
    @ConfigEntry public boolean hideExpandMessages = true;
    @ConfigEntry public boolean hideLuckyCratesMessages = true;
    @ConfigEntry public boolean hideMailMessages = true;
    @ConfigEntry public boolean hideVoteMessages = false;
    @ConfigEntry public boolean removeChatRanks = false;
    @ConfigEntry public boolean allWhiteChat = false;

    @ConfigEntry public boolean enableAutoResponder = false;
    @ConfigEntry public String autoResponderMessage = "I am currently AFK. Please message me on Discord instead at Username#0000.";

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

    @ConfigEntry public boolean enableToolSaver = false;
    @ConfigEntry public int toolSaverDurabilityLimit = 5;

    @ConfigEntry public String shareApiBaseUrl = "https://skyblock.onl";
    @ConfigEntry public boolean enableShareButton = true;


    public static Screen getConfigScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(configInstance, ((defaults, config, builder) -> builder
                .title(Text.translatable("skyplusplus.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.trader-notifications"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.trader-enable"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.trader-enable-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.trader-enable-desc"))
                                *///?}
                                .binding(defaults.enableTraderFinder, () -> config.enableTraderFinder, v -> config.enableTraderFinder = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.trader-enable-title"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.trader-enable-title-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.trader-enable-title-desc"))
                                *///?}
                                .binding(defaults.enableTraderTitles, () -> config.enableTraderTitles, v -> config.enableTraderTitles = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.trader-notif-x"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.trader-notif-x-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.trader-notif-x-desc"))
                                *///?}
                                .binding(defaults.traderX, () -> config.traderX, v -> config.traderX = v)
                                //? if >1.19.2 {
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 4000).step(16))
                                //?} else {
                                /*.controller(opt -> new IntegerSliderController(opt, 0, 200, 1))
                                 *///?}
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.trader-notif-y"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.trader-notif-y-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.trader-notif-y-desc"))
                                *///?}
                                .binding(defaults.traderY, () -> config.traderY, v -> config.traderY = v)
                                //? if >1.19.2 {
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 4000).step(16))
                                //?} else {
                                /*.controller(opt -> new IntegerSliderController(opt, 0, 4000, 16))
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.chatfilter.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidevisitmsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidevisitmsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidevisitmsgs-desc"))
                                *///?}
                                .binding(defaults.hideVisitingMessages, () -> config.hideVisitingMessages, v -> config.hideVisitingMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidevisittitles"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidevisittitles-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidevisittitles-desc"))
                                *///?}
                                .binding(defaults.hideVisitingTitle, () -> config.hideVisitingTitle, v -> config.hideVisitingTitle = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidesbmsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidesbmsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidesbmsgs-desc"))
                                *///?}
                                .binding(defaults.hideSkyblockMessages, () -> config.hideSkyblockMessages, v -> config.hideSkyblockMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideunscramble"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hideunscramble-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hideunscramble-desc"))
                                *///?}
                                .binding(defaults.hideUnscramblingMessages, () -> config.hideUnscramblingMessages, v -> config.hideUnscramblingMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideafk"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hideafk-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hideafk-desc"))
                                *///?}
                                .binding(defaults.hideAFKMessages, () -> config.hideAFKMessages, v -> config.hideAFKMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidenewusermsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidenewusermsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidenewusermsgs-desc"))
                                *///?}
                                .binding(defaults.hideNewUserMessages, () -> config.hideNewUserMessages, v -> config.hideNewUserMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs-desc"))
                                *///?}
                                .binding(defaults.hideDeathsMessages, () -> config.hideDeathsMessages, v -> config.hideDeathsMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hiderafflemsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hiderafflemsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hiderafflemsgs-desc"))
                                *///?}
                                .binding(defaults.hideRaffleMessages, () -> config.hideRaffleMessages, v -> config.hideRaffleMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideadvancementmsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidedeathcountmsgs-desc"))
                                *///?}
                                .binding(defaults.hideAdvancementMessages, () -> config.hideAdvancementMessages, v -> config.hideAdvancementMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidebroadcastmsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidebroadcastmsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidebroadcastmsgs-desc"))
                                *///?}
                                .binding(defaults.hideBroadcastMessages, () -> config.hideBroadcastMessages, v -> config.hideBroadcastMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideislandexpansionmsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hideislandexpansionmsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hideislandexpansionmsgs-desc"))
                                *///?}
                                .binding(defaults.hideExpandMessages, () -> config.hideExpandMessages, v -> config.hideExpandMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hideluckycratemsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hideluckycratemsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hideluckycratemsgs-desc"))
                                *///?}
                                .binding(defaults.hideLuckyCratesMessages, () -> config.hideLuckyCratesMessages, v -> config.hideLuckyCratesMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidemailmsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidemailmsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidemailmsgs-desc"))
                                *///?}
                                .binding(defaults.hideMailMessages, () -> config.hideMailMessages, v -> config.hideMailMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.hidevotemsgs"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.hidevotemsgs-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.hidevotemsgs-desc"))
                                *///?}
                                .binding(defaults.hideVoteMessages, () -> config.hideVoteMessages, v -> config.hideVoteMessages = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.chatfilter.removechatranks"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.chatfilter.removechatranks-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.chatfilter.removechatranks-desc"))
                                *///?}
                                .binding(defaults.removeChatRanks, () -> config.removeChatRanks, v -> config.removeChatRanks = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.autoresponder.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.autoresponder.enable"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.autoresponder.enable-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.autoresponder.enable-desc"))
                                *///?}
                                .binding(defaults.enableAutoResponder, () -> config.enableAutoResponder, v -> config.enableAutoResponder = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.autoresponder.message-desc"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.autoresponder.message")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.autoresponder.message"))
                                *///?}
                                .binding(defaults.autoResponderMessage, () -> config.autoResponderMessage, v -> config.autoResponderMessage = v)
                                //? if >1.19.2 {
                                .controller(StringControllerBuilder::create)
                                //?} else {
                                /*.controller(StringController::new)
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.message-prefix-suffix.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-desc"))
                                *///?}
                                .binding(defaults.chatPrefix, () -> config.chatPrefix, v -> config.chatPrefix = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-desc"))
                                *///?}
                                .binding(defaults.chatSuffix, () -> config.chatSuffix, v -> config.chatSuffix = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-message"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-message-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.prefix-message-desc"))
                                *///?}
                                .binding(defaults.chatPrefixMessage, () -> config.chatPrefixMessage, v -> config.chatPrefixMessage = v)
                                //? if >1.19.2 {
                                .controller(StringControllerBuilder::create)
                                //?} else {
                                /*.controller(StringController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-message"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-message-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.message-prefix-suffix.suffix-message-desc"))
                                *///?}
                                .binding(defaults.chatSuffixMessage, () -> config.chatSuffixMessage, v -> config.chatSuffixMessage = v)
                                //? if >1.19.2 {
                                .controller(StringControllerBuilder::create)
                                //?} else {
                                /*.controller(StringController::new)
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.better-items-uis.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.changebiome"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.better-items-uis.changebiome-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.better-items-uis.changebiome-desc"))
                                *///?}
                                .binding(defaults.betterChangeBiomeEnabled, () -> config.betterChangeBiomeEnabled, v -> config.betterChangeBiomeEnabled = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.better-crate-keys"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.better-items-uis.better-crate-keys-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.better-items-uis.better-crate-keys-desc"))
                                *///?}
                                .binding(defaults.betterCrateKeysEnabled, () -> config.betterCrateKeysEnabled, v -> config.betterCrateKeysEnabled = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.show-empty-shops"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.better-items-uis.show-empty-shops-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.better-items-uis.show-empty-shops-desc"))
                                *///?}
                                .binding(defaults.showEmptyShopsEnabled, () -> config.showEmptyShopsEnabled, v -> config.showEmptyShopsEnabled = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.better-items-uis.dynamic-scoreboard-title"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.better-items-uis.dynamic-scoreboard-title-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.better-items-uis.dynamic-scoreboard-title-desc"))
                                *///?}
                                .binding(defaults.dynamicScoreboardTitle, () -> config.dynamicScoreboardTitle, v -> config.dynamicScoreboardTitle = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.tweaks-improvements.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.extratab"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tweaks-improvements.extratab-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.extratab-desc"))
                                *///?}
                                .binding(defaults.extraTabEnabled, () -> config.extraTabEnabled, v -> config.extraTabEnabled = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-head-dropping"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-head-dropping-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-head-dropping-desc"))
                                *///?}
                                .binding(defaults.preventHeadDropping, () -> config.preventHeadDropping, v -> config.preventHeadDropping = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-grass-placing"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-grass-placing-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.prevent-grass-placing-desc"))
                                *///?}
                                .binding(defaults.antiGrassPlace, () -> config.antiGrassPlace, v -> config.antiGrassPlace = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.autoraffle"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tweaks-improvements.autoraffle-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.autoraffle-desc"))
                                *///?}
                                .binding(defaults.autoRaffleEnabled, () -> config.autoRaffleEnabled, v -> config.autoRaffleEnabled = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.antipc"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tweaks-improvements.antipc-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.antipc-desc"))
                                *///?}
                                .binding(defaults.antiPCEnabled, () -> config.antiPCEnabled, v -> config.antiPCEnabled = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.automatic-lowercase-commands"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tweaks-improvements.automatic-lowercase-commands-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.automatic-lowercase-commands-desc"))
                                *///?}
                                .binding(defaults.lowerCommandsEnabled, () -> config.lowerCommandsEnabled, v -> config.lowerCommandsEnabled = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.tweaks-improvements.join-commands"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tweaks-improvements.join-commands-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tweaks-improvements.join-commands-desc"))
                                *///?}
                                .binding(defaults.joinCommandsList, () -> config.joinCommandsList, v -> config.joinCommandsList = v)
                                //? if >1.19.2 {
                                .controller(StringControllerBuilder::create)
                                //?} else {
                                /*.controller(StringController::new)
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.discord-rpc.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.discord-rpc.enable"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.discord-rpc.enable-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.discord-rpc.enable-desc"))
                                *///?}
                                .binding(defaults.enableDiscordRPC, () -> config.enableDiscordRPC, v -> config.enableDiscordRPC = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.auto-advertisements.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.economy-enable"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.auto-advertisements.economy-enable-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.auto-advertisements.economy-enable-desc"))
                                *///?}
                                .binding(defaults.enableEconomyAdverts, () -> config.enableEconomyAdverts, v -> config.enableEconomyAdverts = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.survival-enable"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.auto-advertisements.survival-enable-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.auto-advertisements.survival-enable-desc"))
                                *///?}
                                .binding(defaults.enableSurvivalAdverts, () -> config.enableSurvivalAdverts, v -> config.enableSurvivalAdverts = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.economy-advertisement"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.auto-advertisements.economy-advertisement-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.auto-advertisements.economy-advertisement-desc"))
                                *///?}
                                .binding(defaults.economyAdMessage, () -> config.economyAdMessage, v -> config.economyAdMessage = v)
                                //? if >1.19.2 {
                                .controller(StringControllerBuilder::create)
                                //?} else {
                                /*.controller(StringController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.survival-advertisement"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.auto-advertisements.survival-advertisement-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.auto-advertisements.survival-advertisement-desc"))
                                *///?}
                                .binding(defaults.survivalAdMessage, () -> config.survivalAdMessage, v -> config.survivalAdMessage = v)
                                //? if >1.19.2 {
                                .controller(StringControllerBuilder::create)
                                //?} else {
                                /*.controller(StringController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.economy-interval"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.auto-advertisements.economy-interval-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.auto-advertisements.economy-interval-desc"))
                                *///?}
                                .binding(defaults.economyAdInterval, () -> config.economyAdInterval, v -> config.economyAdInterval = v)
                                //? if >1.19.2 {
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(TimeConsts.MINIMUM_ECONOMY / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second).step(TimeConsts.Minute / TimeConsts.Second))
                                //?} else {
                                /*.controller(opt -> new IntegerSliderController(opt, TimeConsts.MINIMUM_ECONOMY / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second, TimeConsts.Minute / TimeConsts.Second))
                                 *///?}
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.auto-advertisements.survival-interval"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.auto-advertisements.survival-interval-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.auto-advertisements.survival-interval-desc"))
                                *///?}
                                .binding(defaults.survivalAdInterval, () -> config.survivalAdInterval, v -> config.survivalAdInterval = v)
                                //? if >1.19.2 {
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(TimeConsts.MINIMUM_SKYBLOCK / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second).step(TimeConsts.Minute / TimeConsts.Second))
                                //?} else {
                                /*.controller(opt -> new IntegerSliderController(opt, TimeConsts.MINIMUM_SKYBLOCK / TimeConsts.Second, TimeConsts.Day / TimeConsts.Second, TimeConsts.Minute / TimeConsts.Second))
                                 *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.tool-saver.title"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.tool-saver.enable"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tool-saver.enable-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tool-saver.enable-desc"))
                                *///?}
                                .binding(defaults.enableToolSaver, () -> config.enableToolSaver, v -> config.enableToolSaver = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(int.class)
                                .name(Text.translatable("skyplusplus.config.tool-saver.durability-limit"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.tool-saver.durability-limit-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.tool-saver.durability-limit-desc"))
                                *///?}
                                .binding(defaults.toolSaverDurabilityLimit, () -> config.toolSaverDurabilityLimit, v -> config.toolSaverDurabilityLimit = v)
                                //? if >1.19.2 {
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(0, 200).step(1))
                                //?} else {
                                /*.controller(opt -> new IntegerSliderController(opt, 0, 200, 1))
                                *///?}
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("skyplusplus.config.share.title"))
                        .option(Option.createBuilder(String.class)
                                .name(Text.translatable("skyplusplus.config.share.api-base-url"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.share.api-base-url-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.share.api-base-url-desc"))
                                *///?}
                                .binding(defaults.shareApiBaseUrl , () -> config.shareApiBaseUrl , v -> config.shareApiBaseUrl  = v)
                                //? if >1.19.2 {
                                .controller(StringControllerBuilder::create)
                                //?} else {
                                /*.controller(StringController::new)
                                 *///?}
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("skyplusplus.config.share.button"))
                                //? if >1.19.2 {
                                .description(OptionDescription.of(Text.translatable("skyplusplus.config.share.button-desc")))
                                //?} else {
                                /*.tooltip(Text.translatable("skyplusplus.config.share.button-desc"))
                                *///?}
                                .binding(defaults.enableShareButton , () -> config.enableShareButton , v -> config.enableShareButton  = v)
                                //? if >1.19.2 {
                                .controller(TickBoxControllerBuilder::create)
                                //?} else {
                                /*.controller(TickBoxController::new)
                                 *///?}
                                .build())
                        .build())
        )).generateScreen(parentScreen);
    }
}
