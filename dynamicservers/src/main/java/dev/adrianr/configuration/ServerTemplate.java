package dev.adrianr.configuration;

import dev.adrianr.common.Backup;
import dev.adrianr.common.Server;

public class ServerTemplate {

	private String displayName, allocationIp, allocationPorts;
	private int maxServers;
	private Server serverSettings;
	private Backup backup;

	public ServerTemplate(String displayName, String allocationIp, String allocationPorts, int maxServers, Server serverSettings,
			Backup backup) {
		this.displayName = displayName;
		this.allocationIp = allocationIp;
		this.allocationPorts = allocationPorts;
		this.maxServers = maxServers;
		this.serverSettings = serverSettings;
		this.backup = backup;
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

	public Backup getBackupSettings() {
		return backup;
	}

	public void setBackupSettings(Backup backupSettings) {
		backup = backupSettings;
	}

	public String getAllocationIp() {
		return allocationIp;
	}

	public void setAllocationIp(String allocationIp) {
		this.allocationIp = allocationIp;
	}

	public String getAllocationPorts() {
		return allocationPorts;
	}

	public void setAllocationPorts(String allocationPorts) {
		this.allocationPorts = allocationPorts;
	}

	@Override
	public String toString() {
		return "ServerTemplate [backup=" + backup + ", displayName=" + displayName + ", maxServers=" + maxServers
				+ ", serverSettings=" + serverSettings + "]";
	}

	

}
