package dev.adrianr.commands;

import dev.adrianr.configuration.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command{

	private ConfigManager configManager;

	public ReloadCommand(ConfigManager configManager) {
		super("dsreload");
		this.configManager = configManager;		
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String message;
		if (this.configManager.reloadConfig()){
			message = configManager.getMessage("config-reload-successful", null);
		} else {
			message = configManager.getMessage("config-reload-failed", null);
		}
		sender.sendMessage(new ComponentBuilder(message).create());
	}
	
}
