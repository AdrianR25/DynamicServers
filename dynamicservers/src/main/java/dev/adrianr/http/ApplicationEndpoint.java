package dev.adrianr.http;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
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

		Request request = ApiManager.prepareHttpRequest(url + ROUTE + id, applicationKey);

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

				JsonElement ownerId = ApiManager.getJsonElement(json, "attributes.user");
				server.setOwnerId(ownerId.isJsonNull() ? null : ownerId.getAsInt());
				JsonElement name = ApiManager.getJsonElement(json, "attributes.name");
				server.setName(name.isJsonNull() ? null : name.getAsString());
				JsonElement nodeId = ApiManager.getJsonElement(json, "attributes.node");
				server.setNodeId(nodeId.isJsonNull() ? null : nodeId.getAsInt());

				server.setDatabaseLimit(ApiManager.getJsonElement(json, "attributes.feature_limits.databases").getAsInt());
				JsonElement allocationLimit = ApiManager.getJsonElement(json, "attributes.feature_limits.allocations");
				server.setAllocationLimit(allocationLimit.isJsonNull() ? null : allocationLimit.getAsInt());
				JsonElement backupLimit = ApiManager.getJsonElement(json, "attributes.feature_limits.backups");
				server.setBackupLimit(backupLimit.isJsonNull() ? null : backupLimit.getAsInt());

				JsonElement memory = ApiManager.getJsonElement(json, "attributes.limits.memory");
				server.setMemory(memory.isJsonNull() ? null : memory.getAsInt());
				JsonElement swap = ApiManager.getJsonElement(json, "attributes.limits.swap");
				server.setSwap(swap.isJsonNull() ? null : swap.getAsInt());
				JsonElement diskSpace = ApiManager.getJsonElement(json, "attributes.limits.disk");
				server.setDiskSpace(diskSpace.isJsonNull() ? null : diskSpace.getAsInt());
				JsonElement blockIoWeight = ApiManager.getJsonElement(json, "attributes.limits.io");
				server.setBlockIoWeight(blockIoWeight.isJsonNull() ? null : blockIoWeight.getAsInt());
				JsonElement cpuLimit = ApiManager.getJsonElement(json, "attributes.limits.cpu");
				server.setCpuLimit(cpuLimit.isJsonNull() ? null : cpuLimit.getAsInt());
				
				JsonElement cpuPinning = ApiManager.getJsonElement(json, "attributes.limits.threads");
				server.setCpuPinning(cpuPinning.isJsonNull() ? null : cpuPinning.getAsString());
				JsonElement enableOom = ApiManager.getJsonElement(json, "attributes.limits.oom_disabled");
				server.setEnableOom(enableOom.isJsonNull() ? null : !enableOom.getAsBoolean());

				JsonElement eggId = ApiManager.getJsonElement(json, "attributes.egg");
				server.setEggId(eggId.isJsonNull() ? null : eggId.getAsInt());
				JsonElement dockerImage = ApiManager.getJsonElement(json, "attributes.container.image");
				server.setDockerImage(dockerImage.isJsonNull() ? null : dockerImage.getAsString());

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
