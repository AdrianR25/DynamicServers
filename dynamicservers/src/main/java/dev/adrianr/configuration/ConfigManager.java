package dev.adrianr.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import dev.adrianr.common.Backup;
import dev.adrianr.common.Server;
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

	public ServerTemplate getServerTemplate(String templateName) {

		Configuration templateConfig = getConfig().getSection("server-templates." + templateName);
		
		if (templateConfig.getKeys().size() == 0) return null;
		
		String displayName = templateConfig.getString("display-name");
		int maxServers = templateConfig.getInt("max-servers");

		Server serverSettings = getServerSettingsFromConfig(templateConfig.getSection("server-settings"));
		Backup backupSettings = getBackupSettingsFromConfig(templateConfig.getSection("backup-settings"));

		ServerTemplate template = new ServerTemplate(displayName, maxServers, serverSettings, backupSettings);

		return template;

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

	private Server getServerSettingsFromConfig(Configuration serverSettingsConfig) {

		Server server = new Server();

		server.setOwnerId(serverSettingsConfig.getInt("core-details.owner-id"));
		server.setName(serverSettingsConfig.getString("core-details.name"));
		server.setNodeId(serverSettingsConfig.getInt("core-details.node-id"));
		
		server.setDatabaseLimit(serverSettingsConfig.getInt("application-feature-limits.database-limit"));
		server.setAllocationLimit(serverSettingsConfig.getInt("application-feature-limits.allocation-limit"));
		server.setBackupLimit(serverSettingsConfig.getInt("application-feature-limits.backup-limit"));

		server.setMemory(serverSettingsConfig.getInt("resource-management.memory"));
		server.setSwap(serverSettingsConfig.getInt("resource-management.swap"));
		server.setDiskSpace(serverSettingsConfig.getInt("resource-management.disk-space"));
		server.setBlockIoWeight(serverSettingsConfig.getInt("resource-management.block-io-weight"));
		server.setCpuLimit(serverSettingsConfig.getInt("resource-management.cpu-limit"));
		server.setCpuPinning(serverSettingsConfig.getString("resource-management.memory"));
		server.setEnableOom(serverSettingsConfig.getBoolean("resource-management.enable-oom"));

		server.setEggId(serverSettingsConfig.getInt("egg-id"));
		server.setDockerImage(serverSettingsConfig.getString("docker-image"));
		server.setStartupCommand(serverSettingsConfig.getString("startup-command"));

		return server;
	}

	private Backup getBackupSettingsFromConfig(Configuration backupSettingsConfig) {

		String cronjobSyntax = backupSettingsConfig.getString("cronjob-syntax");
		List<String> ignore = backupSettingsConfig.getStringList("ignore");
		Backup backup = new Backup(cronjobSyntax, ignore);

		return backup;
	}

}
