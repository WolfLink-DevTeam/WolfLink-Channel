package org.wolflink.minecraft.actions;

import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Channel;
import org.wolflink.minecraft.Client;
import org.wolflink.minecraft.file.Configuration;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.interfaces.IPlayer;
import org.wolflink.minecraft.network.Network;

import java.util.List;
import java.util.Set;

@Singleton
public class QueryAction {
    @Inject
    Network network;
    @Inject
    Configuration configuration;
    @Inject
    Language language;
    public void listChannels(IPlayer player) {
        Set<Channel> allChannels = network.queryAllChannels();
        if(allChannels == null || allChannels.isEmpty()) {
            player.sendMessage(language.getCantGetAllOnlineClientsInfo());
        } else {
            StringBuilder buffer = new StringBuilder();
            buffer.append(language.getPrefix());
            buffer.append("§a当前可用频道§8(§7ID§8-§7名称§8)§a：\n");
            for (Channel channel : allChannels) {
                buffer.append(" §8| §r");
                buffer.append(channel.getId());
                buffer.append("§r §7- §r");
                buffer.append(channel.getName());
                buffer.append("\n");
            }
            player.sendMessage(buffer.toString());
        }
    }
    public void listServers(IPlayer player) {
        Set<Client> allServers = network.queryAllOnlineClients();
        if(allServers == null || allServers.isEmpty()) {
            player.sendMessage(language.getCantGetAllOnlineClientsInfo());
        } else {
            StringBuilder buffer = new StringBuilder();
            buffer.append(language.getPrefix());
            buffer.append("§a当前在线服务器§8(§7名称§8-§7账户§8)§a：\n");
            for (Client server : allServers) {
                buffer.append(" §8| §r");
                buffer.append(server.getDisplayName());
                buffer.append("§r §7- §r");
                buffer.append(server.getAccount());
                buffer.append("\n");
            }
            player.sendMessage(buffer.toString());
        }
    }
    public void showAnnouncements(IPlayer player) {
        Channel channel = network.queryChannel(configuration.getChannelId());
        if(channel == null) {
            player.sendMessage(language.getCantGetChannelInfo());
        } else {
            List<String> announcements = channel.getAnnouncement();
            for (String text : announcements) {
                player.sendMessage(text);
            }
        }
    }
}
