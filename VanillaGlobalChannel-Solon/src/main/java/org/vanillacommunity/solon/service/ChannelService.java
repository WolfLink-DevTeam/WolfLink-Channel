package org.vanillacommunity.solon.service;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.MsgType;
import org.vanillacommunity.solon.repository.OnlineClientRepository;

/**
 * 频道服务
 * 向指定频道发送广播消息
 * 向所有 Provider 发送全体消息
 */
@Singleton(true)
@Component
public class ChannelService {
    @Inject
    OnlineClientRepository onlineClientRepository;
    @Inject
    ClientService clientService;

    /**
     * 向指定频道内的在线客户端广播，客户端将收到 Json 格式的消息包
     * @param channelId 频道ID
     * @param msgType   消息类型
     * @param text      消息内容
     */
    public void broadcast(int channelId, MsgType msgType, String text) {
        switch (msgType){
            case SYSTEM:
                onlineClientRepository.filterByChannelId(channelId).forEach(onlineClient -> {
                    clientService.sendSystemMsg(onlineClient,text);
                });
                break;
            case CHANNEL:
                onlineClientRepository.filterByChannelId(channelId).forEach(onlineClient -> {
                    clientService.sendChannelMsg(onlineClient,text);
                });
                break;
        }
    }

    /**
     * 向所有在线客户端广播，客户端将收到 Json 格式的消息包
     * @param msgType   消息类型
     * @param text      消息内容
     */
    public void broadcast(MsgType msgType,String text) {
        switch (msgType) {
            case SYSTEM:
                onlineClientRepository.findAll().forEach(onlineClient -> {
                    clientService.sendSystemMsg(onlineClient,text);
                });
                break;
            case CHANNEL:
                onlineClientRepository.findAll().forEach(onlineClient -> {
                    clientService.sendChannelMsg(onlineClient,text);
                });
                break;
        }

    }
}
