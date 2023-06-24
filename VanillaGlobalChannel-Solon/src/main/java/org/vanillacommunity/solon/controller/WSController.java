package org.vanillacommunity.solon.controller;

import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Param;
import org.noear.solon.annotation.ServerEndpoint;
import org.noear.solon.core.message.Listener;
import org.noear.solon.core.message.Message;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.App;
import org.vanillacommunity.solon.IOC;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.entity.provider.Provider;
import org.vanillacommunity.solon.repository.ProviderRepository;
import org.vanillacommunity.solon.service.ProviderService;

import java.io.IOException;
import java.util.Arrays;

@ServerEndpoint(path = "/ws/{account}")
public class WSController implements Listener {
    @Inject
    Logger logger;
    @Inject
    ProviderRepository providerRepository;
    @Inject
    ProviderService providerService;
    @Override
    public void onOpen(Session session) {
        String account = session.param("account");
        String token = session.param("token");
        Provider provider = providerRepository.find(account);
        if (provider == null) {
            closeSession(session,"未能找到："+account+" 用户");
            return;
        }
        if (token == null || (!token.equals(provider.getToken()))) {
            closeSession(session,"Token 不匹配");
            return;
        }
        logger.info(session.getRemoteAddress()+"成功建立连接");
        providerService.login(session);
    }

    @Override
    public void onMessage(Session session, Message message) {
        logger.info(session.getRemoteAddress()+"发来了一条消息："+ message.bodyAsString());
    }

    @Override
    public void onClose(Session session) {
        providerService.logout(session);
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