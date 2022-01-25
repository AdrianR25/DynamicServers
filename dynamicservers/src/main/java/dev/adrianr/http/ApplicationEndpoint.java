package dev.adrianr.http;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import dev.adrianr.DynamicServers;
import dev.adrianr.common.Server;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApplicationEndpoint {
	
	private DynamicServers plugin;

	public ApplicationEndpoint(DynamicServers plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Returns a server from the given internal id
	 *
	 * @param id the internal id of the server
	 * @return the requested server or null if there is an error
	 */
	public Server getServerById(int id) {
		
		final String ROUTE = "/api/application/servers/";
		final String applicationKey = plugin.getConfigManager().getConfig().getString("api.application-key");
		final String url = plugin.getConfigManager().getConfig().getString("api.url");

		Server server = new Server();

		Request request = ApiManager.prepareGetHttpRequest(url + ROUTE + id, applicationKey);

		OkHttpClient client = new OkHttpClient();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()){
				JsonObject json;

				try {
					json = new JsonParser().parse(response.body().string()).getAsJsonObject();
				} catch (JsonSyntaxException | IOException e) {
					e.printStackTrace();
					return null;
				}

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
				
				JsonElement cpuPinning = ApiManager.getJsonElement(json, "attributes.limits.threads");
				server.setCpuPinning(cpuPinning.isJsonNull() ? "" : cpuPinning.getAsString());
				
				server.setEnableOom(!ApiManager.getJsonElement(json, "attributes.limits.oom_disabled").getAsBoolean());

				server.setEggId(ApiManager.getJsonElement(json, "attributes.egg").getAsInt());
				server.setDockerImage(ApiManager.getJsonElement(json, "attributes.container.image").getAsString());
				server.setStartupCommand(ApiManager.getJsonElement(json, "attributes.container.startup_command").getAsString());

				return server;

			} else if (response.code() == 403) {
				plugin.getLogger().warning(plugin.getConfigManager().getMessage("request-403", null));
			} else if (response.code() == 404) {
				plugin.getLogger().warning(plugin.getConfigManager().getMessage("request-404", null));
			} else {
				plugin.getLogger().warning(plugin.getConfigManager().getMessage("connection-error-generic", null) + " Error: " + response.code());
			}

			return null;
        } catch (IOException e) {
			e.printStackTrace();
			return null;

        }

	}

}
