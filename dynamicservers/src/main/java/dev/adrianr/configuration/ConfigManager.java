package dev.adrianr.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigManager {

	private Plugin plugin;
	private Configuration configuration;

	public ConfigManager(Plugin plugin) {
		this.plugin = plugin;
		createConfig();
		loadConfig();
	}

	/**
	 * 
	 * @return the plugin's config
	 */
	public Configuration getConfig(){
		return this.configuration;
	}

	/**
	 * Reloads the configuration
	 * @return true if the config was reloaded successfully, false otherwise
	 */
	public boolean reloadConfig() {
		return loadConfig();
	}

	/**
	 * Gets a String from the message section of the config
	 * @param messageName
	 * @param username
	 * @return the string, formatted with username and colors
	 */
	public String getMessage(String messageName, String username) {

		String rawMessage = getConfig().getString("messages." + messageName);

		if (username != null) {
			rawMessage.replace("{username}", username);
		}

		String finalMessage = ChatColor.translateAlternateColorCodes('&', rawMessage);
		return finalMessage;
	}

	private boolean loadConfig(){
		try {
			this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void createConfig() {
		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdir();

		File file = new File(plugin.getDataFolder(), "config.yml");

		if (!file.exists()) {
			try (InputStream in = plugin.getResourceAsStream("config.yml")) {
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
