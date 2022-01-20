package dev.adrianr.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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

	public Configuration getConfig(){
		return this.configuration;
	}

	public void reloadConfig() {
		loadConfig();
	}

	private void loadConfig(){
		try {
			this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
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
