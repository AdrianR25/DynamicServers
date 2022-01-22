package dev.adrianr.http;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiManager {

	/**
	 * Returns a JSON sub-element from the given JsonElement and the given path
	 *
	 * @param json a Gson JsonElement
	 * @param path a JSON path, e.g. a.b.c[2].d
	 * @return a sub-element of json according to the given path
	 */
	public static JsonElement getJsonElement(JsonElement json, String path) {

		String[] parts = path.split("\\.|\\[|\\]");
		JsonElement result = json;

		for (String key : parts) {

			key = key.trim();
			if (key.isEmpty())
				continue;

			if (result == null) {
				result = JsonNull.INSTANCE;
				break;
			}

			if (result.isJsonObject()) {
				result = ((JsonObject) result).get(key);
			} else if (result.isJsonArray()) {
				int ix = Integer.valueOf(key) - 1;
				result = ((JsonArray) result).get(ix);
			} else
				break;
		}

		return result;
	}
	// Thanks isapir on StackOverflow <3

	/**
	 *
	 * @param url to request to
	 * @param key a key to authenticate with
	 * @return a response from a request or null if there was an error
	 */
	public static Response makeHttpRequest(String url, String key){
		OkHttpClient client = new OkHttpClient();
        
		Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + key)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
			return response;
        } catch (IOException e) {
			return null;
        }
	}

}
