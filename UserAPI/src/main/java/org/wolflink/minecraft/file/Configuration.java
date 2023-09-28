package org.wolflink.minecraft.file;

import lombok.Getter;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.interfaces.ILogger;

import java.io.IOException;
import java.nio.file.Path;

@Getter
@Singleton
public class Configuration extends YamlConfiguration {

    private String centralServerIp = "";
    private String centralServerPort = "";
    private String account = "";
    private String password = "";
    private int channelId = 1;

    public Configuration() {
        // TODO 改为 PlatformAdapter 提供的数据文件夹路径
        super("config");
    }

    public void load() {
        loadRoot();
        centralServerIp = root.node("CentralServer","Ip").getString("127.0.0.1");
        centralServerPort = root.node("CentralServer","Port").getString("18080");
        account = root.node("User","Account").getString("temp_account");
        password = root.node("User","Password").getString("temp_password");
        channelId = root.node("User","ChannelId").getInt(1);
    }
}
