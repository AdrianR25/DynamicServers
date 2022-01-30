package dev.adrianr;

import java.util.logging.Level;

import dev.adrianr.commands.ReloadCommand;
import dev.adrianr.commands.TestCommand;
import dev.adrianr.configuration.ConfigManager;
import dev.adrianr.pterodactyl.PterodactylApplicationEndpoint;
import dev.adrianr.pterodactyl.PterodactylClientEndpoint;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class DynamicServers extends Plugin {

    ConfigManager configManager;
    PterodactylClientEndpoint clientEndpoint;
    PterodactylApplicationEndpoint applicationEndpoint;

    @Override
    public void onEnable() {

        getLogger().log(Level.INFO, "Loading configuration files");
        configManager = new ConfigManager(this);

        clientEndpoint = new PterodactylClientEndpoint(this);
        applicationEndpoint = new PterodactylApplicationEndpoint(this);

        getLogger().log(Level.INFO, "Registering commands");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TestCommand(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadCommand(this));

    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PterodactylClientEndpoint getClientEndpoint() {
        return clientEndpoint;
    }

    public PterodactylApplicationEndpoint getApplicationEndpoint() {
        return applicationEndpoint;
    }   

}
