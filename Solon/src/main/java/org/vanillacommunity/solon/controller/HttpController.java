package org.vanillacommunity.solon.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.vanillacommunity.solon.config.MainConfig;
import org.vanillacommunity.solon.repository.OnlineClientRepository;
import org.vanillacommunity.solon.repository.SecureChannelRepository;
import org.vanillacommunity.solon.repository.SecureClientRepository;
import org.wolflink.common.ioc.IOC;
import org.wolflink.minecraft.Channel;
import org.wolflink.minecraft.Client;
import org.wolflink.minecraft.HttpAPI;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Controller
@Mapping("/query")
public class HttpController implements HttpAPI {
    private int queryPerMinute = 0;

    public HttpController() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                queryPerMinute = 0;
            }
        }, 1000 * 60, 1000 * 60);
    }

    private synchronized boolean verifyQPM() {
        return (queryPerMinute++ < IOC.getBean(MainConfig.class).getQueryPerMinuteLimit());
    }

    /**
     * 查询指定客户端信息
     */
    @Mapping("/client")
    @Override
    public Client queryClient(String client_account) {
        if (!verifyQPM()) return null;
        return IOC.getBean(SecureClientRepository.class).find(client_account);
    }

    /**
     * 查询指定频道信息
     */
    @Mapping("/channel/info")
    @Override
    public Channel queryChannel(int channel_id) {
        if (!verifyQPM()) return null;
        return IOC.getBean(SecureChannelRepository.class).find(channel_id);
    }

    /**
     * 查询频道在线客户端信息
     */
    @Mapping("/channel/online_clients")
    @Override
    public Set<Client> queryChannelOnlineClients(int channel_id) {
        if (!verifyQPM()) return null;
        return IOC.getBean(OnlineClientRepository.class).filterByChannelId(channel_id).stream()
                .map(it -> (Client) it).collect(Collectors.toSet());
    }
}
