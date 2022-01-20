package dev.adrianr;

import java.util.logging.Level;

import dev.adrianr.commands.TestCommand;
import dev.adrianr.configuration.ConfigManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class DynamicServers extends Plugin {

    ConfigManager configManager;

    @Override
    public void onEnable() {

        getLogger().log(Level.INFO, "Loading configuration files");
        configManager = new ConfigManager(this);

        getLogger().log(Level.INFO, "Registering commands");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TestCommand());

    }
}
