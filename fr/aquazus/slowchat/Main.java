package fr.aquazus.slowchat;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aquazus.slowchat.commands.PausechatCommand;
import fr.aquazus.slowchat.commands.SlowchatCommand;
import fr.aquazus.slowchat.listeners.SlowchatListener;
import fr.aquazus.slowchat.misc.AquAPI;
import fr.aquazus.slowchat.misc.Metrics;
import fr.aquazus.slowchat.util.SlowUtils;
import fr.aquazus.slowchat.util.Utils;

public class Main extends JavaPlugin {
	public static Plugin plugin;
	public static String pluginVersion;

	public static String config_language;
	public static int config_version;
	public static boolean config_metrics;
	public static boolean config_updater;

	public static boolean outdated;

	@Override
	public void onEnable() {
		plugin = this;
		pluginVersion = getDescription().getVersion();
		saveDefaultConfig();
		config_version = getConfig().getInt("configversion");
		if (config_version == 2) {
			Utils.logToConsole(false, "Config is up to date!");
		} else {
			Utils.logToConsole(false, "Config is outdated! Renaming the old one to 'config_old.yml' and creating a updated one");
			File configFile = new File(plugin.getDataFolder(), "config.yml");
			File oldConfigFile = new File(plugin.getDataFolder(), "config_old.yml");
			if (oldConfigFile.exists()) {
				oldConfigFile.delete();
			}
			configFile.renameTo(oldConfigFile);
			saveDefaultConfig();
		}

		config_language = getConfig().getString("language");
		config_metrics = getConfig().getBoolean("metrics");
		config_updater = getConfig().getBoolean("updater");

		if (config_metrics) {
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e) {
				Utils.logToConsole(true, "Failed to load Metrics");
			}
		}

		if (config_language.equalsIgnoreCase("en")) {
			Utils.logToConsole(false, "Setting language to EN (English)");
			Lang.english();
		} else if (config_language.equalsIgnoreCase("fr")) {
			Utils.logToConsole(false, "Setting language to FR (French)");
			Lang.french();
		} else if (config_language.equalsIgnoreCase("de")) {
			Utils.logToConsole(false, "Setting language to DE (German)");
			Lang.german();
		} else {
			Utils.logToConsole(true, "Language '" + config_language + "' not found, setting language to EN (English)");
			Lang.english();
		}

		getServer().getPluginManager().registerEvents(new SlowchatListener(), this);
		SlowUtils.init();
		getCommand("slowchat").setExecutor(new SlowchatCommand());
		getCommand("pausechat").setExecutor(new PausechatCommand());
		Utils.logToConsole(false, "Plugin enabled!");

		if (config_updater) {
			Utils.logToConsole(false, "Checking for updates...");
			if (AquAPI.isUpToDate(pluginVersion)) {
				outdated = false;
				Utils.logToConsole(false, "SlowChat is up to date!");
			} else {
				outdated = true;
				Utils.logToConsole(true, "SlowChat is outdated! You are using " + pluginVersion + " but the latest version is " + AquAPI.getLatestVersionNumber() + "!");
			}
		}
	}

	@Override
	public void onDisable() {
		Utils.logToConsole(false, "Plugin disabled!");
	}
}
