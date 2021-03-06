package org.royaldev.royalcommands;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.royaldev.royalcommands.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private final RoyalCommands plugin;

    public Config(RoyalCommands instance) {
        plugin = instance;
        File config = new File(plugin.getDataFolder(), "config.yml");
        if (!config.exists()) {
            if (!config.getParentFile().mkdirs()) plugin.getLogger().warning("Could not create config.yml directory.");
            plugin.saveDefaultConfig();
        }
        reloadConfiguration();
    }

    public void reloadConfiguration() {
        plugin.reloadConfig();
        final FileConfiguration c = plugin.getConfig();

        //-- Booleans --//

        backDeath = c.getBoolean("back_on_death", true);
        backpackReset = c.getBoolean("reset_backpack_death", false);
        buildPerm = c.getBoolean("use_build_perm", false);
        changeNameTag = c.getBoolean("change_nametag", false);
        checkVersion = c.getBoolean("version_check", true);
        cooldownAliases = c.getBoolean("cooldowns_match_aliases", true);
        customHelp = c.getBoolean("use_custom_help", false);
        disablegetip = c.getBoolean("disable_getip", false);
        dropExtras = c.getBoolean("drop_extras", false);
        dumpCreateChest = c.getBoolean("dump_create_chest", true);
        dumpUseInv = c.getBoolean("dump_use_inv", true);
        explodeFire = c.getBoolean("explode_fire", false);
        h2Convert = c.getBoolean("h2.convert", false);
        kitPerms = c.getBoolean("use_exclusive_kit_perms", false);
        motdLogin = c.getBoolean("motd_on_login", true);
        multiverseNames = c.getBoolean("multiverse_world_names", true);
        otherHelp = c.getBoolean("other_plugins_in_help", true);
        overrideRespawn = c.getBoolean("override_respawn", true);
        purgeUnusedUserdata = c.getBoolean("save.purge_unused_userdata_handlers", true);
        removePotionEffects = c.getBoolean("remove_potion_effects", true);
        requireHelm = c.getBoolean("helm_require_item", false);
        safeTeleport = c.getBoolean("safe_teleport", true);
        sendToSpawn = c.getBoolean("send_to_spawn", false);
        separateEnder = c.getBoolean("worldmanager.inventory_separation.separate_ender_chests", true);
        separateInv = c.getBoolean("worldmanager.inventory_separation.enabled", false);
        separateXP = c.getBoolean("worldmanager.inventory_separation.separate_xp", true);
        showcommands = c.getBoolean("view_commands", true);
        simpleList = c.getBoolean("simple_list", true);
        smoothTime = c.getBoolean("use_smooth_time", true);
        stsBack = c.getBoolean("sts_back", false);
        stsNew = c.getBoolean("send_to_spawn_new", true);
        teleportSoundEnabled = c.getBoolean("teleport_sound.enabled", false);
        timeBroadcast = c.getBoolean("broadcast_time_changes", false);
        updateCheck = c.getBoolean("update_check", false);
        useVNP = c.getBoolean("use_vanish", true);
        useWelcome = c.getBoolean("enable_welcome_message", true);
        useWhitelist = c.getBoolean("use_whitelist", false);
        useWorldManager = c.getBoolean("worldmanager.enabled", true);
        warpPermissions = c.getBoolean("warp_permissions", false);
        wmShowEmptyWorlds = c.getBoolean("worldmanager.who.show_empty_worlds", false);
        worldAccessPerm = c.getBoolean("enable_worldaccess_perm", false);
        ymlConvert = c.getBoolean("yml_convert", false);

        //-- ConfigurationSections --//

        homeLimits = c.getConfigurationSection("home_limits");
        warnActions = c.getConfigurationSection("actions_on_warn");

        //-- Doubles --//

        defaultNear = c.getDouble("default_near_radius", 50D);
        findIpPercent = c.getDouble("findip_alert_percentage", 25D);
        gTeleCd = c.getDouble("global_teleport_cooldown", 0D);
        maxNear = c.getDouble("max_near_radius", 2000D);

        //-- Floats --//

        explodePower = (float) c.getDouble("explode_power", 4F);
        maxExplodePower = (float) c.getDouble("max_explode_power", 10F);
        teleportSoundPitch = (float) c.getDouble("teleport_sound.pitch", 1F);
        teleportSoundVolume = (float) c.getDouble("teleport_sound.volume", 1F);

        //-- Integers --//

        defaultStack = c.getInt("default_stack_size", 64);
        helpAmount = c.getInt("help_lines", 5);
        maxBackStack = c.getInt("max_back_stack", 5);
        spawnmobLimit = c.getInt("spawnmob_limit", 15);
        teleportWarmup = c.getInt("teleport_warmup", 0);

        //-- String lists --//

        blockedItems = c.getStringList("blocked_spawn_items");
        commandCooldowns = c.getStringList("command_cooldowns");
        disabledCommands = c.getStringList("disabled_commands");
        logBlacklist = c.getStringList("command_log_blacklist");
        motd = c.getStringList("motd");
        muteCmds = c.getStringList("mute_blocked_commands");
        onBanActions = c.getStringList("on_ban");

        //-- Longs --//

        afkAutoTime = c.getLong("auto_afk_time", 300L);
        afkKickTime = c.getLong("afk_kick_time", 120L);
        warnExpireTime = c.getLong("warns_expire_after", 604800L);

        //-- Strings --//

        afkFormat = c.getString("afk_format", "{dispname} is now AFK.");
        banFormat = c.getString("ban_format", "&4Banned&r: {reason}&rnBy {dispname}");
        banMessage = RUtils.colorize(c.getString("default_ban_message", "&4Banhammered!"));
        bcastFormat = RUtils.colorize(c.getString("bcast_format", "&b[&aBroadcast&b]&a "));
        defaultWarn = RUtils.colorize(c.getString("default_warn_message", "You have been warned."));
        igBanFormat = c.getString("ingame_ban_format", "&7{kdispname}&c was banned by &7{dispname}&c for &7{reason}&c.");
        igKickFormat = c.getString("ingame_kick_format", "&7{kdispname}&c was kicked by &7{dispname}&c for &7{reason}&c.");
        igTempbanFormat = c.getString("ingame_tempban_format", "&7{kdispname}&c was tempbanned by &7{dispname}&c for &7{length}&c for &7{reason}&c.");
        igUnbanFormat = c.getString("ingame_unban_message", "&7{kdispname}&9 was unbanned by &7{dispname}&9.");
        ipBanFormat = c.getString("ipban_format", "&4IP Banned&r: &7{ip}&r has been banned from this server.");
        kickFormat = c.getString("kick_format", "&4Kicked&r: {reason}&rnBy {dispname}");
        kickMessage = RUtils.colorize(c.getString("default_kick_message", "Kicked from server."));
        mailCheckTime = c.getString("mail_check_interval", "10m");
        nickPrefix = RUtils.colorize(c.getString("nick_prefix", "*"));
        noBuildMessage = RUtils.colorize(c.getString("no_build_message", "&cYou don't have permission to build!"));
        returnFormat = c.getString("return_format", "{dispname} is no longer AFK.");
        saveInterval = c.getString("save.save_on_interval", "10m");
        teleportSoundName = c.getString("teleport_sound.sound", "ENDERMAN_TELEPORT");
        tempbanFormat = c.getString("tempban_format", "&4Tempbanned&r: {length}&rnFor {reason}&r by {dispname}");
        welcomeMessage = RUtils.colorize(c.getString("welcome_message", "&5Welcome {name} to the server!"));
        whitelistFormat = RUtils.colorize(c.getString("whitelist_format", "You are not whitelisted on this server!"));
        whoFormat = c.getString("who_format", "{prefix}{dispname}");
        whoGroupFormat = c.getString("who_group_format", "{prefix}{group}{suffix}");
        positiveChatColor = c.getString("colors.positive", "BLUE");
        negativeChatColor = c.getString("colors.negative", "RED");
        neutralChatColor = c.getString("colors.neutral", "GRAY");
        resetChatColor = c.getString("colors.reset", "RESET");

        if (plugin.whl.exists()) whitelist = plugin.whl.getStringList("whitelist");

        Help.reloadHelp();

        if (RoyalCommands.wm == null) RoyalCommands.wm = new WorldManager();
        RoyalCommands.wm.reloadConfig();

        try {
            Reader in = new FileReader(new File(plugin.getDataFolder() + File.separator + "items.csv"));
            RoyalCommands.inm = new ItemNameManager(new CSVReader(in).readAll());
        } catch (FileNotFoundException e) {
            plugin.getLogger().warning("items.csv was not found! Item aliases will not be used.");
            RoyalCommands.inm = null;
        } catch (IOException e) {
            plugin.getLogger().warning("Internal input/output error loading items.csv. Item aliases will not be used.");
            RoyalCommands.inm = null;
        }

    }

    //-- Booleans --//

    public static Boolean backDeath;
    public static Boolean backpackReset;
    public static Boolean buildPerm;
    public static Boolean changeNameTag;
    public static Boolean checkVersion;
    public static Boolean cooldownAliases;
    public static Boolean customHelp;
    public static Boolean disablegetip;
    public static Boolean dropExtras;
    public static Boolean dumpCreateChest;
    public static Boolean dumpUseInv;
    public static Boolean explodeFire;
    public static Boolean h2Convert;
    public static Boolean kitPerms;
    public static Boolean motdLogin;
    public static Boolean multiverseNames;
    public static Boolean otherHelp;
    public static Boolean overrideRespawn;
    public static Boolean purgeUnusedUserdata;
    public static Boolean removePotionEffects;
    public static Boolean requireHelm;
    public static Boolean safeTeleport;
    public static Boolean sendToSpawn;
    public static Boolean separateEnder;
    public static Boolean separateInv;
    public static Boolean separateXP;
    public static Boolean showcommands;
    public static Boolean simpleList;
    public static Boolean smoothTime;
    public static Boolean stsBack;
    public static Boolean stsNew;
    public static Boolean teleportSoundEnabled;
    public static Boolean timeBroadcast;
    public static Boolean updateCheck;
    public static Boolean useVNP;
    public static Boolean useWelcome;
    public static Boolean useWhitelist;
    public static Boolean useWorldManager;
    public static Boolean warpPermissions;
    public static Boolean wmShowEmptyWorlds;
    public static Boolean worldAccessPerm;
    public static Boolean ymlConvert;

    //-- ConfigurationSections --//

    public static ConfigurationSection homeLimits;
    public static ConfigurationSection warnActions;

    //-- Doubles --//

    public static Double defaultNear;
    public static Double findIpPercent;
    public static Double gTeleCd;
    public static Double maxNear;

    //-- Floats --//

    public static Float explodePower;
    public static Float maxExplodePower;
    public static Float teleportSoundPitch;
    public static Float teleportSoundVolume;

    //-- Integers --//

    public static Integer defaultStack;
    public static Integer helpAmount;
    public static Integer maxBackStack;
    public static Integer spawnmobLimit;
    public static Integer teleportWarmup;

    //-- String lists --//

    public static List<String> blockedItems = new ArrayList<String>();
    public static List<String> commandCooldowns = new ArrayList<String>();
    public static List<String> disabledCommands = new ArrayList<String>();
    public static List<String> logBlacklist = new ArrayList<String>();
    public static List<String> motd = new ArrayList<String>();
    public static List<String> muteCmds = new ArrayList<String>();
    public static List<String> onBanActions = new ArrayList<String>();
    public static List<String> whitelist = new ArrayList<String>();

    //-- Longs --//

    public static Long afkAutoTime;
    public static Long afkKickTime;
    public static Long warnExpireTime;

    //-- Strings --//

    public static String afkFormat;
    public static String banFormat;
    public static String banMessage;
    public static String bcastFormat;
    public static String defaultWarn;
    public static String igBanFormat;
    public static String igKickFormat;
    public static String igTempbanFormat;
    public static String igUnbanFormat;
    public static String ipBanFormat;
    public static String kickFormat;
    public static String kickMessage;
    public static String mailCheckTime;
    public static String nickPrefix;
    public static String noBuildMessage;
    public static String returnFormat;
    public static String saveInterval;
    public static String teleportSoundName;
    public static String tempbanFormat;
    public static String welcomeMessage;
    public static String whitelistFormat;
    public static String whoFormat;
    public static String whoGroupFormat;
    public static String positiveChatColor;
    public static String negativeChatColor;
    public static String neutralChatColor;
    public static String resetChatColor;


}
