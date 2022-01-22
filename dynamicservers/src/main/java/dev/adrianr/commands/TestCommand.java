package dev.adrianr.commands;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.adrianr.DynamicServers;
import dev.adrianr.configuration.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestCommand extends Command {

    private DynamicServers plugin;

    public TestCommand(DynamicServers plugin) {
        super("dstest");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String url = plugin.getConfigManager().getConfig().getString("api.url");
        String userKey = plugin.getConfigManager().getConfig().getString("api.user-key");

        sender.sendMessage(new ComponentBuilder("Executing test command " + url).create());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/api/client/servers/e948d688")
                .addHeader("Authorization", "Bearer " + userKey)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            sender.sendMessage(new ComponentBuilder(body).create());
            if (response.isSuccessful()){
                JsonObject json = new Gson().fromJson(body, JsonObject.class);
                sender.sendMessage(new ComponentBuilder("Identifier: " + getJsonElement(json, "attributes.identifier").getAsString()).create());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonElement getJsonElement(JsonElement json, String path){

        String[] parts = path.split("\\.|\\[|\\]");
        JsonElement result = json;
    
        for (String key : parts) {
    
            key = key.trim();
            if (key.isEmpty())
                continue;
    
            if (result == null){
                result = JsonNull.INSTANCE;
                break;
            }
    
            if (result.isJsonObject()){
                result = ((JsonObject)result).get(key);
            }
            else if (result.isJsonArray()){
                int ix = Integer.valueOf(key) - 1;
                result = ((JsonArray)result).get(ix);
            }
            else break;
        }
    
        return result;
    }

}
