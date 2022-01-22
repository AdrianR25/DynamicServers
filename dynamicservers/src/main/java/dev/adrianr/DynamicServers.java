package dev.adrianr;

import java.util.logging.Level;

import dev.adrianr.commands.ReloadCommand;
import dev.adrianr.commands.TestCommand;
import dev.adrianr.configuration.ConfigManager;
import dev.adrianr.http.ClientEndpoint;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class DynamicServers extends Plugin {

    ConfigManager configManager;
    ClientEndpoint clientEndpoint;

    @Override
    public void onEnable() {

        getLogger().log(Level.INFO, "Loading configuration files");
        configManager = new ConfigManager(this);
        clientEndpoint = new ClientEndpoint(configManager.getConfig().getString("api.url"), configManager.getConfig().getString("api.user-key"));

        getLogger().log(Level.INFO, "Registering commands");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TestCommand(configManager));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadCommand(configManager));

    }
}
