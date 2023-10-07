package org.wolflink.minecraft.actions;

import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Channel;
import org.wolflink.minecraft.Client;
import org.wolflink.minecraft.Result;
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
    public Result listChannels() {
        Set<Channel> allChannels = network.queryAllChannels();
        if(allChannels == null || allChannels.isEmpty()) {
            return new Result(false,language.getCantGetChannelInfo());
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
            return new Result(true,buffer.toString());
        }
    }
    public Result listServers() {
        Set<Client> allServers = network.queryAllOnlineClients();
        if(allServers == null || allServers.isEmpty()) {
            return new Result(false,language.getCantGetAllOnlineClientsInfo());
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
            return new Result(true,buffer.toString());
        }
    }
    public Result showAnnouncements() {
        Channel channel = network.queryChannel(configuration.getChannelId());
        if(channel == null) {
            return new Result(false,language.getCantGetChannelInfo());
        } else {
            StringBuilder builder = new StringBuilder();
            List<String> announcements = channel.getAnnouncement();
            for (String text : announcements) {
                builder.append(text);
                builder.append('\n');
            }
            return new Result(true,builder.toString());
        }
    }
}
