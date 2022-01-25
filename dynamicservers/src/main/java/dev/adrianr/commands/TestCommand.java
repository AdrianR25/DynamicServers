package dev.adrianr.commands;

import dev.adrianr.DynamicServers;
import dev.adrianr.common.Server;
import dev.adrianr.configuration.ServerTemplate;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class TestCommand extends Command {

    private DynamicServers plugin;

    public TestCommand(DynamicServers plugin) {
        super("dstest");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        sender.sendMessage(new ComponentBuilder("Executing test command").create());

        ServerTemplate template = plugin.getConfigManager().getServerTemplate(args[0]);
        if (template == null) {
            String message = plugin.getConfigManager().getMessage("config-template-not-exists", null);
            sender.sendMessage(new ComponentBuilder(message).create());
        } else {
            sender.sendMessage(new ComponentBuilder(template.toString()).create());
        }


        /*
        Server server = plugin.getApplicationEndpoint().getServerById(Integer.valueOf(args[0]));
        if (server == null) {
            sender.sendMessage(new ComponentBuilder("Couldn't get server").create());
            return;
        }
        sender.sendMessage(new ComponentBuilder(server.toString()).create());*/
    }
}
