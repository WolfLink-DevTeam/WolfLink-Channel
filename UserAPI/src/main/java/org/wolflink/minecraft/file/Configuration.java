package org.wolflink.minecraft.file;

import lombok.Getter;
import org.wolflink.common.ioc.Singleton;

@Getter
@Singleton
public class Configuration extends YamlConfiguration {

    private String centralServerIp;
    private String centralServerWebSocketPort;
    private String centralServerHttpPort;
    private String account;
    private String password;
    private int channelId;
    private boolean forceJoinChannel;
    private boolean repeatToLocalIfFailed;

    public Configuration() {
        // TODO 改为 PlatformAdapter 提供的数据文件夹路径
        super("config");
    }

    public void load() {
        loadRoot();
        centralServerIp = root.node("CentralServer","Ip").getString("127.0.0.1");
        centralServerWebSocketPort = root.node("CentralServer","WebSocket-Port").getString("18080");
        centralServerHttpPort = root.node("CentralServer","Http-Port").getString("8080");
        account = root.node("User","Account").getString("temp_account");
        password = root.node("User","Password").getString("temp_password");
        channelId = root.node("User","ChannelId").getInt(1);
        forceJoinChannel = root.node("Settings","Force-Join-Channel").getBoolean(false);
        repeatToLocalIfFailed = root.node("Settings","Repeat-To-Local-If-Failed").getBoolean(true);
    }
}
