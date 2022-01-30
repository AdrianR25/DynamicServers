package dev.adrianr.pterodactyl;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import dev.adrianr.DynamicServers;
import dev.adrianr.common.Allocation;
import dev.adrianr.common.Server;
import dev.adrianr.http.ApiManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PterodactylApplicationEndpoint {
	
	private DynamicServers plugin;

	public PterodactylApplicationEndpoint(DynamicServers plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Returns a server from the given internal id
	 *
	 * @param id the internal id of the server
	 * @return the requested server or null if there is an error
	 */
	public Server getServerById(int serverId) {
		
		final String ROUTE = "/api/application/servers/" + serverId;
		final String applicationKey = plugin.getConfigManager().getConfig().getString("api.application-key");
		final String url = plugin.getConfigManager().getConfig().getString("api.url");

		Request request = ApiManager.prepareGetHttpRequest(url + ROUTE, applicationKey);

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

				Server server = new Server();

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

	/**
	 * Returns a list of allocations in the given node. The list will be empty if
	 * there are no allocations created in the node.
	 * @param nodeId
	 * @return a list of allocations
	 */
	public ArrayList<Allocation> getAllocationsByNode(int nodeId){
		
		final String ROUTE = "/api/application/nodes/" + nodeId + "/allocations";
		final String applicationKey = plugin.getConfigManager().getConfig().getString("api.application-key");
		final String url = plugin.getConfigManager().getConfig().getString("api.url");

		Request request = ApiManager.prepareGetHttpRequest(url + ROUTE, applicationKey);
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

				ArrayList<Allocation> allocations = new ArrayList<>();

				JsonArray allocationArray = ApiManager.getJsonElement(json, "data").getAsJsonArray();
				if (allocationArray.size() == 0) return allocations;
				
				allocationArray.forEach((JsonElement e) -> {
					String ip = ApiManager.getJsonElement(e, "attributes.ip").getAsString();
					int port = ApiManager.getJsonElement(e, "attributes.port").getAsInt();
					int id = ApiManager.getJsonElement(e, "attributes.id").getAsInt();
					boolean assigned = ApiManager.getJsonElement(e, "attributes.assigned").getAsBoolean();
					allocations.add(new Allocation(ip, port, assigned, id));
				});

				return allocations;

			}
			return null;
        } catch (IOException e) {
			e.printStackTrace();
			return null;

        }
	}

	/**
	 * Creates new allocations in the given node with the given ip and ports
	 * @param nodeId
	 * @param ip
	 * @param ports
	 * @return true if the allocations were created successfully, false otherwise
	 */
	public boolean createAllocations (int nodeId, String ip, int[] ports) {

		final String ROUTE = "/api/application/nodes/" + nodeId + "/allocations";
		final String applicationKey = plugin.getConfigManager().getConfig().getString("api.application-key");
		final String url = plugin.getConfigManager().getConfig().getString("api.url");

		JsonObject rootObject = new JsonObject();
		rootObject.addProperty("ip", ip);

		JsonArray portsArray = new JsonArray();
		for (int port : ports) {
			portsArray.add(String.valueOf(port));
		}
		rootObject.add("ports", portsArray);

		Request request = ApiManager.preparePostHttpRequest(url + ROUTE, applicationKey, rootObject.toString());
		OkHttpClient client = new OkHttpClient();

		try (Response response = client.newCall(request).execute()) {
			if (response.code() == 204){
				return true;
			} else {
				return false;
			}
        } catch (IOException e) {
			e.printStackTrace();
			return false;

        }

	}

	/**
	 * Creates new allocation in the given node with the given ip and port
	 * @param nodeId
	 * @param ip
	 * @param port
	 * @return true if the allocation was created successfully, false otherwise
	 */
	public boolean createAllocation (int nodeId, String ip, int port) {

		int[] ports = {port};
		return createAllocations(nodeId, ip, ports);

	}

}
