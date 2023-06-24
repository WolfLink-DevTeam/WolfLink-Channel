package org.vanillacommunity.solon.service;

import lombok.NonNull;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.Logger;
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
    public void login(Session session) {
        @NonNull
        String account = session.param("account");
        @NonNull
        String token = session.param("token");
        int channelId = Integer.parseInt(session.param("channel_id"));
        onlineClientRepository.update(new OnlineClient(account, token, channelId, session, new Date()));
        logger.info(account+" 成功登录，所在频道 "+channelId);
    }

    public void logout(Session session) {
        @NonNull
        String account = session.param("account");
        int channelId = Integer.parseInt(session.param("channel_id"));
        onlineClientRepository.delete(account);
        logger.info(account+" 已离线，所在频道 "+channelId);
    }

}
