package org.vanillacommunity.solon.service;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.MsgType;
import org.vanillacommunity.solon.entity.message.GlobalMessage;
import org.vanillacommunity.solon.repository.OnlineClientRepository;

import java.util.Objects;

/**
 * 频道层面的业务类
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
    @Inject
    Logger logger;

    /**
     * 向指定频道内的在线客户端广播，客户端将收到 Json 格式的消息包
     * 同时会把消息存储在对应频道的消息容器中
     * @param channelId         频道ID
     * @param msgType           消息类型
     * @param globalMessage     消息对象
     */
    public void broadcast(int channelId, MsgType msgType, GlobalMessage globalMessage) {
        switch (msgType) {
            case SYSTEM:
                logger.warn("不应该广播发送这条系统消息，请检查代码，系统消息如下：" + globalMessage.toString());
                break;
            case ANNOUNCEMENT:
                onlineClientRepository.filterByChannelId(channelId).forEach(onlineClient -> {
                    clientService.sendAnnouncementMsg(onlineClient, globalMessage);
                });
                break;
            case CHANNEL:
                onlineClientRepository.filterByChannelId(channelId).forEach(onlineClient -> {
                    clientService.sendChannelMsg(onlineClient, globalMessage);
                });
                break;
        }
    }

    /**
     * 向所有在线客户端广播，客户端将收到 Json 格式的消息包
     * @param msgType           消息类型
     * @param globalMessage     消息对象
     */
    public void broadcast(MsgType msgType,GlobalMessage globalMessage) {
        switch (msgType) {
            case CHANNEL:
                onlineClientRepository.findAll().forEach(onlineClient -> {
                    clientService.sendChannelMsg(onlineClient,globalMessage);
                });
                break;
            case ANNOUNCEMENT:
                onlineClientRepository.findAll().forEach(onlineClient -> {
                    clientService.sendAnnouncementMsg(onlineClient,globalMessage);
                });
                break;
            case SYSTEM:
                logger.warn("不应该广播发送这条系统消息，请检查代码，系统消息如下：" + globalMessage.toString());
                break;
        }
    }
}
