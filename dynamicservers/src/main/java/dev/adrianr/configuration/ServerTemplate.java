package dev.adrianr.configuration;

import dev.adrianr.common.Server;

public class ServerTemplate {

	private String name;
	private int maxServers;
	private Server serverSettings;
	private BackupSettings BackupSettings;

	public ServerTemplate(String name, int maxServers, Server serverSettings,
			dev.adrianr.configuration.BackupSettings backupSettings) {
		this.name = name;
		this.maxServers = maxServers;
		this.serverSettings = serverSettings;
		BackupSettings = backupSettings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxServers() {
		return maxServers;
	}

	public void setMaxServers(int maxServers) {
		this.maxServers = maxServers;
	}

	public Server getServerSettings() {
		return serverSettings;
	}

	public void setServerSettings(Server serverSettings) {
		this.serverSettings = serverSettings;
	}

	public BackupSettings getBackupSettings() {
		return BackupSettings;
	}

	public void setBackupSettings(BackupSettings backupSettings) {
		BackupSettings = backupSettings;
	}

}
