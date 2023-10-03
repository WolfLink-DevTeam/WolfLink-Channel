package org.wolflink.minecraft.file;

import lombok.Getter;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Client;
import org.wolflink.minecraft.interfaces.IPlayer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Singleton
public class Language extends YamlConfiguration {
    private String prefix;
    private List<String> cmdHelp;
    private String channelLeave;
    private String channelJoin;
    private String alreadyInChannel;
    private String notInChannel;
    private String serverOnline;
    private String isRetrying;
    private String sendFailed;
    public String getServerOnline(Client client) {
        return serverOnline
                .replace("%server%",client.getDisplayName());
    }
    private String serverOffline;
    public String getServerOffline(Client client) {
        return serverOffline
                .replace("%server%",client.getDisplayName());
    }
    private String chatTemplate;
    public String getChatTemplate(String serverName,String senderName,String content) {

        return chatTemplate
                .replace("%server%",serverName)
                .replace("%sender%",senderName)
                .replace("%content%",content);
    }
//    private String announcementTemplate = "[ 全球公告 ] %content%";

    public Language() {
        // TODO 改为 PlatformAdapter 提供的数据文件夹路径
        super("lang");
    }

    public void load() {
        loadRoot();
        prefix = root.node("plugin","prefix").getString("§8[ §9绫狼网络 §8]§7");
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
        channelLeave = root.node("channel","leave").getString("%prefix% 你已离开跨服频道！挥挥~").replace("%prefix%",prefix);
        channelJoin = root.node("channel","join").getString("%prefix% 你已加入频道。").replace("%prefix%",prefix);
        serverOnline = root.node("channel","server-online").getString("%prefix% 服务器 %server% §7加入了跨服聊天。").replace("%prefix%",prefix);
        serverOffline = root.node("channel","server-offline").getString("%prefix% 服务器 %server% §7退出了跨服聊天。").replace("%prefix%",prefix);
        chatTemplate = root.node("channel","chat-template").getString("§7[ %server% §8| §a%sender% §7] §8» §7%content%");
        alreadyInChannel = root.node("channel","already-in").getString("%prefix% §e你已经处在频道中了。").replace("%prefix%",prefix);
        notInChannel = root.node("channel","not-in").getString("%prefix% §e你当前没有处于频道中。").replace("%prefix%",prefix);
        isRetrying = root.node("system","is-retrying").getString("%prefix% §c消息发送失败，正在尝试重新建立连接。").replace("%prefix%",prefix);
        sendFailed = root.node("system","send-failed").getString("%prefix% §e消息发送失败，请稍后再尝试。").replace("%prefix%",prefix);
//        announcementTemplate = root.node("channel").node("announcement-template").getString("");
    }
}
