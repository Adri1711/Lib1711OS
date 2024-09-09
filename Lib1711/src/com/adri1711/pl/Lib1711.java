package com.adri1711.pl;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Lib1711 extends JavaPlugin {

	private Boolean patchPotions;
	private Boolean patchNames;
	private Logger logger;

	public void onEnable() {
		logger=getLogger();
		loadConfig();
		patchPotions = getConfig().getBoolean("patchPotions");
		patchNames = getConfig().getBoolean("patchNames");

		
	}
	

	public Logger getLoggerV() {
		return logger;
	}


	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (label.equalsIgnoreCase("lib1711")) {
			switch (args.length) {
			case 0:
				loadConfig();

				patchPotions = getConfig().getBoolean("patchPotions");
				if (player != null) {
					player.sendMessage("Lib1711 reloaded");
				}
				break;
			default:

				break;

			}
		}

		return true;
	}

	public void onDisable() {
		Bukkit.getServer().getScheduler().cancelTasks((Plugin) this);
	}

	public boolean loadConfig() {
		if (!(new File(getDataFolder() + File.separator + "config.yml")).exists()) {
			saveDefaultConfig();
		}

		try {
			(new YamlConfiguration()).load(new File(getDataFolder() + File.separator + "config.yml"));
		} catch (Exception e) {
			System.out.println("--- --- Lib1711 --- ---");
			System.out.println("There was an error loading your configuration.");
			System.out.println("A detailed description of your error is shown below.");
			System.out.println("--- --- --- ---");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin((Plugin) this);

			return false;
		}
		reloadConfig();

		loadConfiguration();
		return true;
	}

	public void loadConfiguration() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

	public void defaultConfiguration() {

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public Boolean getPatchPotions() {
		return patchPotions;
	}

	public void setPatchPotions(Boolean patchPotions) {
		this.patchPotions = patchPotions;
	}


	public Boolean getPatchNames() {
		return patchNames;
	}


	public void setPatchNames(Boolean patchNames) {
		this.patchNames = patchNames;
	}
	

}
