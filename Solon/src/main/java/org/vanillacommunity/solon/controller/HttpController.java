package org.vanillacommunity.solon.controller;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.vanillacommunity.solon.Logger;
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

    /**
     * 检查是否达到每分钟查询次数阈值
     */
    private synchronized boolean reachQPM() {
        boolean result = (queryPerMinute++ >= IOC.getBean(MainConfig.class).getQueryPerMinuteLimit());
        if(result) IOC.getBean(Logger.class).warn("已达到每分钟查询数阈值，当前查询次数："+queryPerMinute);
        return result;
    }

    /**
     * 查询指定客户端信息
     */
    @Mapping("/client")
    @Override
    public Client queryClient(String client_account) {
        if (reachQPM()) return null;
        if(client_account == null) return null;
        IOC.getBean(Logger.class).info("接口调用：queryClient 参数：client_account="+client_account);
        return IOC.getBean(SecureClientRepository.class).find(client_account).toClient();
    }

    /**
     * 查询指定频道信息
     */
    @Mapping("/channel/info")
    @Override
    public Channel queryChannel(int channel_id) {
        if (reachQPM()) return null;
        IOC.getBean(Logger.class).info("接口调用：queryChannel 参数：channel_id="+channel_id);
        return IOC.getBean(SecureChannelRepository.class).find(channel_id).toChannel();
    }

    @Mapping("/channel/all")
    @Override
    public Set<Channel> queryAllChannels() {
        if(reachQPM()) return null;
        IOC.getBean(Logger.class).info("接口调用：queryAllChannels");
        return IOC.getBean(SecureChannelRepository.class).findAllChannels();
    }

    /**
     * 查询频道在线客户端信息
     */
    @Mapping("/channel/online_clients")
    @Override
    public Set<Client> queryChannelOnlineClients(int channel_id) {
        if (reachQPM()) return null;
        IOC.getBean(Logger.class).info("接口调用：queryChannelOnlineClients 参数：channel_id="+channel_id);
        return IOC.getBean(OnlineClientRepository.class).filterByChannelId(channel_id).stream()
                .map(it -> (Client) it).collect(Collectors.toSet());
    }

    @Mapping("/client/all")
    @Override
    public Set<Client> queryAllOnlineClients() {
        if (reachQPM()) return null;
        IOC.getBean(Logger.class).info("接口调用：queryAllOnlineClients");
        return IOC.getBean(OnlineClientRepository.class).findAllClients();
    }
}
