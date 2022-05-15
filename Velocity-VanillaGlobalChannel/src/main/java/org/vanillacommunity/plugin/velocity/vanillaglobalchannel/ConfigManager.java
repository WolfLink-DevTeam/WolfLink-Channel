package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    public static ConfigurationNode config;
    public static ConfigManager instance;
    private int defaultChannelID;
    private int serverID;
    private String account;
    private String password;
    private String centralServerIP;
    private List<String> filterList;

    private ConfigManager(){}

    public static ConfigManager getInstance() throws ObjectMappingException {
        if(instance == null)
        {
            instance = new ConfigManager();
            instance.init();
        }
        return instance;
    }

    private void init() throws ObjectMappingException {
        defaultChannelID = config.getNode("MinecraftServer.Net-DefaultChannelID").getInt(1);
        serverID = config.getNode("MinecraftServer.Net-ServerID").getInt(-1);
        account = config.getNode("MinecraftServer.Net-Account").getString("none");
        password = config.getNode("MinecraftServer.Net-Password").getString("none");
        centralServerIP = config.getNode("CentralServer-IP").getString();
        filterList = config.getNode("Message-Filter").getList(TypeToken.of(String.class));

    }

    public int getDefaultChannelID() {
        return defaultChannelID;
    }

    public static ConfigurationNode getConfig() {
        return config;
    }

    public List<String> getFilterList() {
        return filterList;
    }

    public String getAccount() {
        return account;
    }

    public String getCentralServerIP() {
        return centralServerIP;
    }

    public String getPassword() {
        return password;
    }

    public int getServerID() {
        return serverID;
    }
}
