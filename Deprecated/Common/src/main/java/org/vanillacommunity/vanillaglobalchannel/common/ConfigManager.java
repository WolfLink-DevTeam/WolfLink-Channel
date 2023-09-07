package org.vanillacommunity.vanillaglobalchannel.common;
import io.leangen.geantyref.TypeToken;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {

    public static String cmdPrefix;
    public static String msgPrefix;
    public static String account;
    public static String password;

    private static ConfigurationNode config;
    public static ConfigManager instance;
    public static String channelMessageFormat;
    public static String pluginStart;
    public static String pluginStart1;
    public static String pluginStart2;
    public static String pluginStart3;
    public static String serverInfo1;
    public static String serverInfoFinish;
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
    public static int filterMode;

    public static int showTips;

    public static boolean autoJoinChannel;

    private ConfigManager(){}

    public static ConfigManager getInstance() {
        if(instance == null)
        {
            instance = new ConfigManager();
        }
        return instance;
    }

    public ConfigurationNode loadConfig(Path path){

        File folder = path.toFile();
        File file = new File(folder, "config.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try (InputStream input = getClass().getResourceAsStream("/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                return null;
            }
        }
        ConfigurationNode result = null;
        try
        {
            result = YamlConfigurationLoader.builder().file(file).build().load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public void init() throws SerializationException {
        if(config == null)config = loadConfig(PlatformAdapter.getInstance().getPath());
        defaultChannelID = config.node( "MinecraftServer").node("Net-DefaultChannelID").getInt(1);
        serverID = config.node("MinecraftServer").node("Net-ServerID").getInt(-1);
        account = config.node("MinecraftServer").node("Net-Account").getString("none");
        password = config.node("MinecraftServer").node("Net-Password").getString("none");
        centralServerIP = config.node("CentralServer-IP").getString();
        filterMode = config.node("Message-Filter-Mode").getInt(0);
        filterList = config.node("Message-Filter").getList(TypeToken.get(String.class));
        channelMessageFormat = config.node("Channel-Message-Format").getString();
        cmdPrefix = config.node("Language").node("CmdPrefix").getString();
        msgPrefix = config.node("Language").node("MsgPrefix").getString();

        pluginStart = config.node("Language").node("Plugin-Start").getString();
        pluginStart = parsePlaceHolder(pluginStart);
        pluginStart1 = config.node("Language").node("Plugin-Start1").getString();
        pluginStart1 = parsePlaceHolder(pluginStart1);
        pluginStart2 = config.node("Language").node("Plugin-Start2").getString();
        pluginStart2 = parsePlaceHolder(pluginStart2);
        pluginStart3 = config.node("Language").node("Plugin-Start3").getString();
        pluginStart3 = parsePlaceHolder(pluginStart3);

        serverInfo1 = config.node("Language").node("Server-GetInfo1").getString();
        serverInfo1 = parsePlaceHolder(serverInfo1);

        serverInfoFinish = config.node("Language").node("Server-GetInfo-Finish").getString();
        serverInfoFinish = parsePlaceHolder(serverInfoFinish);

        pluginStartFinish = config.node("Language").node("Plugin-Start-Finish").getString();
        pluginStartFinish = parsePlaceHolder(pluginStartFinish);
        commandHelp = config.node("Language").node("Command-Help").getList(TypeToken.get(String.class));
        commandLeave = config.node("Language").node("Command-Leave").getString();
        commandLeave = parsePlaceHolder(commandLeave);
        commandChannelDefault = config.node("Language").node("Command-Channel-Default").getString();
        commandChannelDefault = parsePlaceHolder(commandChannelDefault);
        commandChannelDefaultNotfound = config.node("Language").node("Command-Channel-Default-Notfound").getString();
        commandChannelDefaultNotfound = parsePlaceHolder(commandChannelDefaultNotfound);
        commandChannelID = config.node("Language").node("Command-Channel-ID").getString();
        commandChannelID = parsePlaceHolder(commandChannelID);
        commandChannelIDNotfound = config.node("Language").node("Command-Channel-ID-Notfound").getString();
        commandChannelIDNotfound = parsePlaceHolder(commandChannelIDNotfound);

        showTips = config.node("ShowTips").getInt(3600);
        autoJoinChannel = config.node("AutoJoinChannel").getBoolean(false);

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
