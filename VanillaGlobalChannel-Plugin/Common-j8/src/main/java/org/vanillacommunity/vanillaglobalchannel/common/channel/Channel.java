package org.vanillacommunity.vanillaglobalchannel.common.channel;

import lombok.Data;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<String> announcementList = Arrays.asList(announcement.split("\\|"));
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
