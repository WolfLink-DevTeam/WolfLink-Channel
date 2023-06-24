package org.vanillacommunity.vanillaglobalchannel.common.channel;

import lombok.Data;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;

import java.util.List;

/**
 * 基本频道对象
 * 可参考 Solon 端的 Channel 和 CommonChannel
 */
@Data
public class Channel {

    // 频道的唯一数字ID
    private final int id;
    // 频道的展示名称
    private final String displayName;
    // 频道的公告，以 | 符号为换行符
    private String announcement;

    public Channel(int id,String displayName,String announcement)
    {
        this.id = id;
        this.displayName = displayName;
        this.announcement = announcement;
    }

    /**
     * 这个 toString 方法是把属性直接通过 / 符号组织起来，很不规范
     */
    @Override
    public String toString()
    {
        return id+"/"+displayName+"/"+announcement;
    }

    /**
     * 向一个玩家展示公告
     * @param p 玩家接口
     */
    public void showAnnouncement(IPlayer p)
    {
        List<String> announcementList = List.of(announcement.split("\\|"));
        p.sendMessage(" ");
        p.sendMessage("§7[ "+displayName+" §7] §e频道公告");
        p.sendMessage(" ");
        for(String message : announcementList)
        {
            p.sendMessage("§f"+message);
        }
        p.sendMessage(" ");
    }
}
