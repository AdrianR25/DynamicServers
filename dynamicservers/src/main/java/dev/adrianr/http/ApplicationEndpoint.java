package dev.adrianr.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dev.adrianr.DynamicServers;
import dev.adrianr.common.Server;
import okhttp3.Response;

public class ApplicationEndpoint {
	
	private String applicationKey;
	private String url;
	private DynamicServers plugin;

	public ApplicationEndpoint(DynamicServers plugin) {
		this.applicationKey = plugin.getConfigManager().getConfig().getString("api.application-key");
		this.url = plugin.getConfigManager().getConfig().getString("api.url");
		this.plugin = plugin;
	}

	public Server getServerById(int id) {
		
		final String ROUTE = "/api/application/servers/";

		Server server = new Server();

		Response response = ApiManager.makeHttpRequest(url + ROUTE + id, applicationKey);

		if (response == null) return null;

		if (response.isSuccessful()){
			JsonObject json = new Gson().fromJson(response.body().toString(), JsonObject.class);
			server.setOwnerId(ApiManager.getJsonElement(json, "attributes.user").getAsInt());
			server.setName(ApiManager.getJsonElement(json, "attributes.name").getAsString());
			server.setNodeId(ApiManager.getJsonElement(json, "attributes.node").getAsInt());

			server.setDatabaseLimit(ApiManager.getJsonElement(json, "attributes.feature_limits.databases").getAsInt());
			server.setAllocationLimit(ApiManager.getJsonElement(json, "attributes.feature_limits.allocations").getAsInt());
			server.setBackupLimit(ApiManager.getJsonElement(json, "attributes.feature_limits.backups").getAsInt());

			server.setMemory(ApiManager.getJsonElement(json, "attributes.limits.memory").getAsInt());
			server.setSwap(ApiManager.getJsonElement(json, "attributes.limits.swap").getAsInt());
			server.setDiskSpace(ApiManager.getJsonElement(json, "attributes.limits.disk").getAsInt());
			server.setBlockIoWeight(ApiManager.getJsonElement(json, "attributes.limits.io").getAsInt());
			server.setCpuLimit(ApiManager.getJsonElement(json, "attributes.limits.cpu").getAsInt());
			server.setCpuPinning(ApiManager.getJsonElement(json, "attributes.limits.threads").getAsString());
			server.setEnableOom(!ApiManager.getJsonElement(json, "attributes.limits.oom_disabled").getAsBoolean());

			server.setEggId(ApiManager.getJsonElement(json, "attributes.egg").getAsInt());
			server.setDockerImage(ApiManager.getJsonElement(json, "attributes.container.image").getAsString());

			return server;

		} else if (response.code() == 403) {
			plugin.getLogger().warning(plugin.getConfigManager().getMessage("request-403", null));
		} else if (response.code() == 404) {
			plugin.getLogger().warning(plugin.getConfigManager().getMessage("request-404", null));
		} else {
			plugin.getLogger().warning(plugin.getConfigManager().getMessage("connection-error-generic", null) + " Error: " + response.code());
		}

		return null;

	}

}
