package org.vanillacommunity.solon.service;

import com.google.gson.JsonObject;
import lombok.NonNull;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.entity.client.Client;
import org.vanillacommunity.solon.entity.client.OnlineClient;
import org.vanillacommunity.solon.repository.OnlineClientRepository;

import java.util.Date;

@Singleton(true)
@Component
public class ClientService {
    @Inject
    OnlineClientRepository onlineClientRepository;
    @Inject
    Logger logger;
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
    public void sendSystemMsg(OnlineClient onlineClient,String msg) {
        JsonObject jo = new JsonObject();
        jo.addProperty("type","system");
        jo.addProperty("msg",msg);
        onlineClient.getSession().sendAsync(jo.toString());
    }

    /**
     * 发送频道消息
     */
    public void sendChannelMsg(OnlineClient onlineClient,String msg) {
        JsonObject jo = new JsonObject();
        jo.addProperty("type","channel");
        jo.addProperty("msg",msg);
        onlineClient.getSession().sendAsync(jo.toString());
    }

}
