package org.wolflink.minecraft.file;

import lombok.Getter;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Application;

import java.io.IOException;
import java.nio.file.Path;

@Getter
@Singleton
public class Configuration {

    private String centralServerIp = "";
    private String centralServerPort = "";
    private String account = "";
    private String password = "";
    private int channelId = 1;

    public void load() {
        // TODO 改为 PlatformAdapter 提供的数据文件夹路径
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(Path.of("GlobalChannel/config.yml"))
                .build();
        CommentedConfigurationNode root;
        try {
            root = loader.load();
        } catch (IOException e) {
            Application.getLogger().err("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
            System.exit(1);
            return;
        }
        centralServerIp = root.node("CentralServer").node("Ip").getString("");
        centralServerPort = root.node("CentralServer").node("Port").getString("");
        account = root.node("User").node("Account").getString("");
        password = root.node("User").node("Password").getString("");
        channelId = root.node("User").node("ChannelId").getInt(1);
    }
}
