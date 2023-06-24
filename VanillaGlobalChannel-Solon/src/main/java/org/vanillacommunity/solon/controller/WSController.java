package org.vanillacommunity.solon.controller;

import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.ServerEndpoint;
import org.noear.solon.core.message.Listener;
import org.noear.solon.core.message.Message;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.entity.client.OnlineClient;
import org.vanillacommunity.solon.entity.client.Client;
import org.vanillacommunity.solon.repository.ChannelRepository;
import org.vanillacommunity.solon.repository.ClientRepository;
import org.vanillacommunity.solon.repository.OnlineClientRepository;
import org.vanillacommunity.solon.service.ClientService;
import org.vanillacommunity.solon.service.WebSocketService;

import java.io.IOException;

@ServerEndpoint(path = "/ws/{account}")
public class WSController implements Listener {
    @Inject
    Logger logger;
    @Inject
    ClientRepository clientRepository;
    @Inject
    OnlineClientRepository onlineClientRepository;
    @Inject
    ChannelRepository channelRepository;
    @Inject
    ClientService clientService;
    @Inject
    WebSocketService webSocketService;
    @Override
    public void onOpen(Session session) {
        String account = session.param("account");
        String token = session.param("token");
        int channelId;
        try {
            channelId = Integer.parseInt(session.param("channel_id"));
        } catch (Exception ignore) {
            closeSession(session,"频道ID "+session.param("channel_id")+" 不合法");
            return;
        }
        if(channelRepository.find(channelId) == null) {
            closeSession(session,"频道ID "+channelId+" 未找到");
            return;
        }

        Client client = clientRepository.find(account);
        if (client == null) {
            closeSession(session,"未能找到："+account+" 用户");
            return;
        }
        if (token == null || (!token.equals(client.getToken()))) {
            closeSession(session,"Token 不匹配");
            return;
        }
        logger.info(session.getRemoteAddress()+"成功建立连接");
        clientService.login(client,session,channelId);
    }

    @Override
    public void onMessage(Session session, Message message) {
        OnlineClient onlineClient = onlineClientRepository.find(session.param("account"));
        if(onlineClient == null) {
            logger.warn("收到了来自 "+session.param("account")+" 的消息，但该客户端不在线。");
            return;
        }
        webSocketService.analyseMessage(onlineClient,message);
        logger.info(session.getRemoteAddress()+"发来了一条消息："+ message.bodyAsString());
    }

    @Override
    public void onClose(Session session) {
        OnlineClient onlineClient = onlineClientRepository.find(session.param("account"));
        if(onlineClient != null) { clientService.logout(onlineClient); }
        logger.info(session.getRemoteAddress()+"断开了连接");
    }

    @Override
    public void onError(Session session, Throwable error) {
        logger.err(session.getRemoteAddress()+"触发了一个错误："+error.toString());
    }
    void closeSession(Session session,String reason) {
        try {
            session.close();
            logger.warn("尝试关闭来自 "+session.getRemoteAddress()+" 的连接，理由："+reason);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}