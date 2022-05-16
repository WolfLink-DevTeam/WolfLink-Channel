package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class ConfigManager {

    public static String cmdPrefix;
    public static String msgPrefix;
    public static String account;
    public static String password;

    public static ConfigurationNode config;
    public static ConfigManager instance;
    public static String channelMessageFormat;
    public static String pluginStart;
    public static String pluginStart1;
    public static String pluginStart2;
    public static String pluginStart3;
    public static String pluginStartFinish;
    public static List<String> commandHelp;
    public static String commandLeave;
    public static String commandChannelDefault;
    public static String commandChannelDefaultNotfound;
    public static String commandChannelID;
    public static String commandChannelIDNotfound;
    public static int defaultChannelID;
    public static int serverID;
    public static String centralServerIP;
    public static List<String> filterList;

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
        defaultChannelID = config.getNode("MinecraftServer").getNode("Net-DefaultChannelID").getInt(1);
        serverID = config.getNode("MinecraftServer").getNode("Net-ServerID").getInt(-1);
        account = config.getNode("MinecraftServer").getNode("Net-Account").getString("none");
        password = config.getNode("MinecraftServer").getNode("Net-Password").getString("none");
        centralServerIP = config.getNode("CentralServer-IP").getString();
        filterList = config.getNode("Message-Filter").getList(TypeToken.of(String.class));
        channelMessageFormat = config.getNode("Channel-Message-Format").getString();
        cmdPrefix = config.getNode("Language").getNode("CmdPrefix").getString();
        msgPrefix = config.getNode("Language").getNode("MsgPrefix").getString();

        pluginStart = config.getNode("Language").getNode("Plugin-Start").getString();
        pluginStart = parsePlaceHolder(pluginStart);
        pluginStart1 = config.getNode("Language").getNode("Plugin-Start1").getString();
        pluginStart1 = parsePlaceHolder(pluginStart1);
        pluginStart2 = config.getNode("Language").getNode("Plugin-Start2").getString();
        pluginStart2 = parsePlaceHolder(pluginStart2);
        pluginStart3 = config.getNode("Language").getNode("Plugin-Start3").getString();
        pluginStart3 = parsePlaceHolder(pluginStart3);
        pluginStartFinish = config.getNode("Language").getNode("Plugin-Start-Finish").getString();
        pluginStartFinish = parsePlaceHolder(pluginStartFinish);
        commandHelp = config.getNode("Language").getNode("Command-Help").getList(TypeToken.of(String.class));
        commandLeave = config.getNode("Language").getNode("Command-Leave").getString();
        commandLeave = parsePlaceHolder(commandLeave);
        commandChannelDefault = config.getNode("Language").getNode("Command-Channel-Default").getString();
        commandChannelDefault = parsePlaceHolder(commandChannelDefault);
        commandChannelDefaultNotfound = config.getNode("Language").getNode("Command-Channel-Default-Notfound").getString();
        commandChannelDefaultNotfound = parsePlaceHolder(commandChannelDefaultNotfound);
        commandChannelID = config.getNode("Language").getNode("Command-Channel-ID").getString();
        commandChannelID = parsePlaceHolder(commandChannelID);
        commandChannelIDNotfound = config.getNode("Language").getNode("Command-Channel-ID-Notfound").getString();
        commandChannelIDNotfound = parsePlaceHolder(commandChannelIDNotfound);

    }

    /**
     * 用来将ConfigManager类中的配置文本内包含的变量全部替换。
     * MessageFormat除外！
     */
    private String parsePlaceHolder(String str)
    {
        str = str.replaceAll("%CmdPrefix%",cmdPrefix);
        str = str.replaceAll("%MsgPrefix%",msgPrefix);
        return str;
    }
}
