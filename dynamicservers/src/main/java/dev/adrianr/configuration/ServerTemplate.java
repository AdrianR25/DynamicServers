package dev.adrianr.configuration;

import dev.adrianr.common.Server;

public class ServerTemplate {

	private String displayName;
	private int maxServers;
	private Server serverSettings;
	private BackupSettings BackupSettings;

	public ServerTemplate(String displayName, int maxServers, Server serverSettings,
			dev.adrianr.configuration.BackupSettings backupSettings) {
		this.displayName = displayName;
		this.maxServers = maxServers;
		this.serverSettings = serverSettings;
		BackupSettings = backupSettings;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
