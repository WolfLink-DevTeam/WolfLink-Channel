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

    private final int id;
    private final String displayName;
    private String announcement;

    public Channel(int id,String displayName,String announcement)
    {
        this.id = id;
        this.displayName = displayName;
        this.announcement = announcement;
    }

    @Override
    public String toString()
    {
        return id+"/"+displayName+"/"+announcement;
    }

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
