package org.vanillacommunity.solon.service;

import com.google.gson.JsonObject;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.message.Message;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.MsgType;
import org.vanillacommunity.solon.entity.client.OnlineClient;
import org.vanillacommunity.solon.entity.data.DataPack;
import org.vanillacommunity.solon.entity.message.GlobalMessage;
import org.vanillacommunity.solon.repository.OnlineClientRepository;

@Singleton(true)
@Component
public class WebSocketService {
    @Inject
    ChannelService channelService;
    @Inject
    OnlineClientRepository onlineClientRepository;
    @Inject
    Logger logger;
    /**
     * 规范化Websocket交互信息
     * @param msgType       消息类型
     * @param globalMessage 消息内容
     */
    public String formatData(MsgType msgType,GlobalMessage globalMessage) {
        return new DataPack(msgType,globalMessage.toJson().toString()).toJson().toString();
    }

    /**
     * 从给定的JSON格式字符串解析数据包
     * @param input JSON格式字符串
     * @return      DataPack对象
     */
    public DataPack unpackData(String input) {
        return DataPack.fromJson(input);
    }

    /**
     * 解析客户端发来的数据
     */
    public void analyseMessage(Message message) {
        DataPack dataPack = unpackData(message.bodyAsString());
        // 客户端发来频道消息，则需要广播这条频道消息到客户端所处频道中
        if(dataPack.getType() == MsgType.CHANNEL) {
            GlobalMessage globalMessage = GlobalMessage.fromJson(dataPack.getContent());
            OnlineClient onlineClient = onlineClientRepository.find(globalMessage.getClientAccount());
            if(onlineClient == null) {
                logger.warn("收到了来自 "+globalMessage.getClientAccount()+" 的数据包，但该客户端当前不在线。");
                return;
            }
            int channelId = onlineClient.getChannelId();
            // 把这条消息广播给对应频道
            channelService.broadcast(channelId,MsgType.CHANNEL,globalMessage);
        }
    }
}
