package dev.adrianr.configuration;

public class BackupSettings {

	private String cronjobSyntax;
	private String[] ignore;

	public BackupSettings(String cronjobSyntax, String[] ignore) {
		this.cronjobSyntax = cronjobSyntax;
		this.ignore = ignore;
	}

	public String getCronjobSyntax() {
		return cronjobSyntax;
	}

	public void setCronjobSyntax(String cronjobSyntax) {
		this.cronjobSyntax = cronjobSyntax;
	}

	public String[] getIgnore() {
		return ignore;
	}

	public void setIgnore(String[] ignore) {
		this.ignore = ignore;
	}

}
