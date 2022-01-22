package dev.adrianr.commands;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.adrianr.configuration.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestCommand extends Command {

    private ConfigManager configManager;

    public TestCommand(ConfigManager configManager) {
        super("dstest");
        this.configManager = configManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        String url = configManager.getConfig().getString("api.url");
        String userKey = configManager.getConfig().getString("api.user-key");

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
                sender.sendMessage(new ComponentBuilder("Identifier: " + json.get("object").toString()).create());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
