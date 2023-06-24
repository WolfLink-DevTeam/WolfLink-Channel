package org.vanillacommunity.solon.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.MsgType;
import org.vanillacommunity.solon.entity.message.GlobalMessage;
import org.vanillacommunity.solon.entity.client.Client;
import org.vanillacommunity.solon.entity.client.OnlineClient;
import org.vanillacommunity.solon.repository.OnlineClientRepository;

import java.util.Date;

/**
 * 对于一个具体客户端的业务类
 */
@Singleton(true)
@Component
public class ClientService {
    @Inject OnlineClientRepository onlineClientRepository;
    @Inject WebSocketService webSocketService;
    @Inject Logger logger;
    public void login(Client client,Session session,int channelId) {
        OnlineClient onlineClient = new OnlineClient(client.getAccount(), client.getToken(), channelId, session, new Date());
        onlineClientRepository.update(onlineClient);
        logger.info(client.getAccount()+" 成功登录，所在频道 "+channelId);
    }
    public void logout(OnlineClient onlineClient) {
        onlineClientRepository.delete(onlineClient.getAccount());
        logger.info(onlineClient.getAccount()+" 已离线，所在频道 "+onlineClient.getChannelId());
    }
    /**
     * 发送系统消息
     */
    public void sendSystemMsg(OnlineClient onlineClient, JsonElement jsonElement) {
        onlineClient.getSession().sendAsync(
                webSocketService.formatData(MsgType.SYSTEM,jsonElement)
        );
    }

    /**
     * 发送公告消息
     * @param onlineClient      在线客户端
     * @param globalMessage     消息对象
     */
    public void sendAnnouncementMsg(OnlineClient onlineClient,GlobalMessage globalMessage) {
        onlineClient.getSession().sendAsync(
                webSocketService.formatData(MsgType.ANNOUNCEMENT,globalMessage)
        );
    }
    /**
     * 发送频道消息
     */
    public void sendChannelMsg(OnlineClient onlineClient, GlobalMessage globalMessage) {
        onlineClient.getSession().sendAsync(
                webSocketService.formatData(MsgType.CHANNEL,globalMessage)
        );
    }

}
