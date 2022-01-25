package dev.adrianr.common;

import java.util.List;

public class Backup {

	private String cronjobSyntax;
	private List<String> ignore;

	public Backup(String cronjobSyntax, List<String> ignore) {
		this.cronjobSyntax = cronjobSyntax;
		this.ignore = ignore;
	}

	public String getCronjobSyntax() {
		return cronjobSyntax;
	}

	public void setCronjobSyntax(String cronjobSyntax) {
		this.cronjobSyntax = cronjobSyntax;
	}

	public List<String> getIgnore() {
		return ignore;
	}

	public void setIgnore(List<String> ignore) {
		this.ignore = ignore;
	}

	@Override
	public String toString() {
		return "Backup [cronjobSyntax=" + cronjobSyntax + ", ignore=" + ignore + "]";
	}
	

}
