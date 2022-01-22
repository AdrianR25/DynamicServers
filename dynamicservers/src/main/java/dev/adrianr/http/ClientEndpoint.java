package dev.adrianr.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dev.adrianr.DynamicServers;
import dev.adrianr.common.Server;
import okhttp3.Response;

public class ClientEndpoint {
	
	private String userKey;
	private String url;
	private DynamicServers plugin;

	public ClientEndpoint(DynamicServers plugin) {
		this.userKey = plugin.getConfigManager().getConfig().getString("api.user-key");
		this.url = plugin.getConfigManager().getConfig().getString("api.url");
		this.plugin = plugin;
	}

}
