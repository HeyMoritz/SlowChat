package fr.aquazus.slowchat.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.aquazus.slowchat.Lang;
import fr.aquazus.slowchat.util.SlowUtils;

public class PausechatCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("slowchat.use.pause")) {
			if (args.length == 0 || args.length > 1) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.PAUSECHAT_COMMAND_USAGE));
			} else {
				if (args[0].equalsIgnoreCase("on")) {
					SlowUtils.pausemode = true;
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.PAUSEMODE_ENABLED));
					Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', Lang.SERVER_IS_NOW_IN_PAUSEMODE));
				} else if (args[0].equalsIgnoreCase("off")) {
					SlowUtils.pausemode = false;
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.PAUSEMODE_DISABLED));
					Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', Lang.SERVER_IS_NO_LONGER_IN_PAUSEMODE));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.PAUSECHAT_COMMAND_USAGE));
				}
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Lang.NO_PERMISSION));
		}
		return true;
	}
}