package dev.adrianr.http;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MojangEndpoint {
	
	public static String getPlayerUUID(String username){
		String url = "https://api.mojang.com/users/profiles/minecraft/" + username;
		Request request = ApiManager.prepareGetHttpRequest(url);

		OkHttpClient client = new OkHttpClient();
		try (Response response = client.newCall(request).execute()) {
			if (response.code() == 204) {
				return null;
			} else {
				JsonObject json;
				try {
					json = new JsonParser().parse(response.body().string()).getAsJsonObject();
				} catch (JsonSyntaxException | IOException e) {
					e.printStackTrace();
					return null;
				}

				return ApiManager.getJsonElement(json, "id").getAsString();

			}
        } catch (IOException e) {
			e.printStackTrace();
			return null;

        }


	}

}
