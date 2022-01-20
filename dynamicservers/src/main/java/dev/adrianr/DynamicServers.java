package dev.adrianr;

import dev.adrianr.commands.TestCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class DynamicServers extends Plugin {
    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TestCommand());
    }
}
