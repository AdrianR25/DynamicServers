package dev.adrianr.configuration;

public class ServerTemplate {

	private String name;
	private int maxServers;
	private ServerSettings serverSettings;
	private BackupSettings BackupSettings;

	public ServerTemplate(String name, int maxServers, ServerSettings serverSettings,
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

	public ServerSettings getServerSettings() {
		return serverSettings;
	}

	public void setServerSettings(ServerSettings serverSettings) {
		this.serverSettings = serverSettings;
	}

	public BackupSettings getBackupSettings() {
		return BackupSettings;
	}

	public void setBackupSettings(BackupSettings backupSettings) {
		BackupSettings = backupSettings;
	}

}
