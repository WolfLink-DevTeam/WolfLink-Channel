package org.vanillacommunity.solon.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.vanillacommunity.solon.entity.OnlineClient;
import org.vanillacommunity.solon.repository.OnlineClientRepository;
import org.vanillacommunity.solon.repository.SecureChannelRepository;
import org.vanillacommunity.solon.repository.SecureClientRepository;
import org.wolflink.common.ioc.IOC;
import org.wolflink.minecraft.Channel;
import org.wolflink.minecraft.Client;

import java.util.Set;

@Controller
@Mapping("/query")
public class HttpController {
    @Mapping("/client")
    public Client queryClient(String client_account) {
        return IOC.getBean(SecureClientRepository.class).find(client_account);
    }

    @Mapping("/channel")
    public Channel queryChannel(int channel_id) {
        return IOC.getBean(SecureChannelRepository.class).find(channel_id);
    }

    @Mapping("/channel_online_clients")
    public Set<OnlineClient> queryChannel_online_clients(int channel_id) {
        return IOC.getBean(OnlineClientRepository.class).filterByChannelId(channel_id);
    }
}
