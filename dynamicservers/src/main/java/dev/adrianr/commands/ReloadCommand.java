package dev.adrianr.commands;

import dev.adrianr.DynamicServers;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command{

	private DynamicServers plugin;

	public ReloadCommand(DynamicServers plugin) {
		super("dsreload");
		this.plugin = plugin;		
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String message;
		if (plugin.getConfigManager().reloadConfig()){
			message = plugin.getConfigManager().getMessage("config-reload-successful", null);
		} else {
			message = plugin.getConfigManager().getMessage("config-reload-failed", null);
		}
		sender.sendMessage(new ComponentBuilder(message).create());
	}
	
}
