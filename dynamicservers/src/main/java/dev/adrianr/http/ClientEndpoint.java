package dev.adrianr.http;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dev.adrianr.common.Server;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientEndpoint {
	
	private String userKey;
	private String url;

	public ClientEndpoint(String url, String clientKey) {
		this.userKey = clientKey;
		this.url = url;
	}

	public Server getServerByIdentifier(String identifier) {
		
		Server server = new Server();

		OkHttpClient client = new OkHttpClient();
        
		Request request = new Request.Builder()
                .url(url + "/api/client/servers/" + identifier)
                .addHeader("Authorization", "Bearer " + userKey)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				JsonObject json = new Gson().fromJson(response.body().toString(), JsonObject.class);
				//server.setOwnerId(json.get("attributes"));
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return server;
	}

}
