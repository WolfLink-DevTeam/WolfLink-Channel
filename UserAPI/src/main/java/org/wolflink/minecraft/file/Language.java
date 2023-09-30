package org.wolflink.minecraft.file;

import lombok.Getter;
import org.wolflink.common.ioc.Singleton;

import java.util.ArrayList;
import java.util.List;

@Getter
@Singleton
public class Language extends YamlConfiguration {
    private String prefix;
    private List<String> cmdHelp;
    private String channelLeave;
    private String channelJoin;
    private String serverOnline;
    private String serverOffline;
    private String chatTemplate;
//    private String announcementTemplate = "[ 全球公告 ] %content%";

    public Language() {
        // TODO 改为 PlatformAdapter 提供的数据文件夹路径
        super("lang");
    }

    public void load() {
        loadRoot();
        prefix = root.node("plugin","prefix").getString("[ GlobalChannel ]");
        try {
            cmdHelp = root.node("plugin","cmdHelp").getList(String.class);
        } catch (Exception ignore) {
            List<String> temp = new ArrayList<>();
            temp.add(" ");
            temp.add("§8[ §9绫狼网络 §8] §f指令帮助 §7» ");
            temp.add(" ");
            temp.add("§b/channel §8- §7查询指令帮助");
            temp.add("§b/channel join §8- §7加入默认频道");
            temp.add("§b/channel leave §8- §7离开当前频道");
            temp.add(" ");
            cmdHelp = temp;
        }
        channelLeave = root.node("channel","leave").getString("%prefix% 你已离开跨服频道！挥挥~");
        channelJoin = root.node("channel","join").getString("%prefix% 你已加入频道。");
        serverOnline = root.node("channel","server-online").getString("%prefix% 一台服务器加入了跨服聊天。");
        serverOffline = root.node("channel","server-offline").getString("%prefix% 一台服务器退出了跨服聊天。");
        chatTemplate = root.node("channel","chat-template").getString("§7[ §a%sender% §7] §8» §7%content%");
//        announcementTemplate = root.node("channel").node("announcement-template").getString("");
    }
}
