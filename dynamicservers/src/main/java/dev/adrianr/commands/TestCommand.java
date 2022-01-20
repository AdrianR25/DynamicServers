package dev.adrianr.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class TestCommand extends Command {

    public TestCommand() {
        super("dstest");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(new ComponentBuilder("Executing test command").create());

    }

}
