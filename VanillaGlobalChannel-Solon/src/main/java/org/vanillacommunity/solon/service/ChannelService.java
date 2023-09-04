package org.vanillacommunity.solon.service;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.entity.Channel;
import org.vanillacommunity.solon.repository.ChannelRepository;
import org.vanillacommunity.solon.repository.OnlineClientRepository;
import org.wolflink.minecraft.GlobalMessage;
import org.wolflink.minecraft.MsgType;

/**
 * 频道层面的业务类
 * 向指定频道发送广播消息
 * 向所有 Provider 发送全体消息
 */
@Singleton(true)
@Component
public class ChannelService {
    @Inject
    ChannelRepository channelRepository;
    @Inject
    OnlineClientRepository onlineClientRepository;
    @Inject
    ClientService clientService;
    @Inject
    Logger logger;

    /**
     * 向指定频道内的在线客户端广播，客户端将收到 Json 格式的消息包
     * 同时会把消息存储在对应频道的消息容器中
     *
     * @param channelId     频道ID
     * @param msgType       消息类型
     * @param globalMessage 消息对象
     */
    public void broadcast(int channelId, MsgType msgType, GlobalMessage globalMessage) {
        Channel channel = channelRepository.find(channelId);
        if (channel == null) {
            logger.err("在尝试向ID为 " + channelId + " 的频道播报消息时出现错误，并不存在该ID的频道！");
            return;
        }
        switch (msgType) {
            case SYSTEM:
                logger.warn("不应该广播发送这条系统消息，请检查代码，系统消息如下：" + globalMessage.toString());
                break;
            case ANNOUNCEMENT:
                // 将消息添加到聊天记录中
                channel.getMessageContainer().add(globalMessage);
                // 发送消息
                onlineClientRepository.filterByChannelId(channelId).forEach(onlineClient -> {
                    clientService.sendAnnouncementMsg(onlineClient, globalMessage);
                });
                break;
            case CHANNEL:
                // 将消息添加到聊天记录中
                channel.getMessageContainer().add(globalMessage);
                // 发送消息
                onlineClientRepository.filterByChannelId(channelId).forEach(onlineClient -> {
                    clientService.sendChannelMsg(onlineClient, globalMessage);
                });
                break;
        }
    }

    /**
     * 向所有在线客户端广播，客户端将收到 Json 格式的消息包
     *
     * @param msgType       消息类型
     * @param globalMessage 消息对象
     */
    public void broadcast(MsgType msgType, GlobalMessage globalMessage) {
        channelRepository.findAll().forEach(channel -> {
            broadcast(channel.getId(), msgType, globalMessage);
        });
    }
}
