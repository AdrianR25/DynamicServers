package dev.adrianr.commands;

import dev.adrianr.DynamicServers;
import dev.adrianr.common.Server;
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

        Server server = plugin.getApplicationEndpoint().getServerById(Integer.valueOf(args[0]));
        if (server == null) {
            sender.sendMessage(new ComponentBuilder("Couldn't get server").create());
            return;
        }
        sender.sendMessage(new ComponentBuilder(server.toString()).create());
    }
}
