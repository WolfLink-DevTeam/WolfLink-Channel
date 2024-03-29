package org.vanillacommunity.solon.controller;

import org.noear.solon.annotation.ServerEndpoint;
import org.noear.solon.core.message.Listener;
import org.noear.solon.core.message.Message;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.IPMatcher;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.entity.OnlineClient;
import org.vanillacommunity.solon.entity.SecureClient;
import org.vanillacommunity.solon.repository.OnlineClientRepository;
import org.vanillacommunity.solon.repository.SecureChannelRepository;
import org.vanillacommunity.solon.repository.SecureClientRepository;
import org.vanillacommunity.solon.service.ClientService;
import org.vanillacommunity.solon.service.WebSocketService;
import org.wolflink.common.ioc.IOC;

import java.io.IOException;

@ServerEndpoint(path = "/connection")
public class WSController implements Listener {
    Logger logger = IOC.getBean(Logger.class);
    SecureClientRepository secureClientRepository = IOC.getBean(SecureClientRepository.class);
    OnlineClientRepository onlineClientRepository = IOC.getBean(OnlineClientRepository.class);
    SecureChannelRepository secureChannelRepository = IOC.getBean(SecureChannelRepository.class);
    ClientService clientService = IOC.getBean(ClientService.class);
    WebSocketService webSocketService = IOC.getBean(WebSocketService.class);

    @Override
    public void onOpen(Session session) {
        String address = session.getRemoteAddress().getAddress().getHostAddress();
        String account = session.param("account");
        String password = session.param("password");
        int channelId;
        try {
            channelId = Integer.parseInt(session.param("channel_id"));
        } catch (Exception ignore) {
            closeSession(session, "频道ID " + session.param("channel_id") + " 不合法");
            return;
        }
        if (secureChannelRepository.find(channelId) == null) {
            closeSession(session, "频道ID " + channelId + " 未找到");
            return;
        }

        SecureClient secureClient = secureClientRepository.find(account);
        if (secureClient == null) {
            closeSession(session, "未能找到：" + account + " 用户");
            return;
        }
        if (password == null || (!password.equals(secureClient.getPassword()))) {
            closeSession(session, "Password 不匹配");
            return;
        }
        boolean isMatch = false;
        for (String ipSegment : secureClient.getIpSegments()) {
            if (IPMatcher.isIpMatch(address, ipSegment)) {
                isMatch = true;
                break;
            }
        }
        if (!isMatch) {
            closeSession(session, "IP段不在用户白名单内");
            return;
        }
        logger.info(session.getRemoteAddress() + "成功建立连接");
        clientService.login(secureClient, session, channelId);
    }

    @Override
    public void onMessage(Session session, Message message) {
        OnlineClient onlineClient = onlineClientRepository.find(session.param("account"));
        if (onlineClient == null) {
            logger.warn("收到了来自 " + session.param("account") + " 的消息，但该客户端不在线。");
            return;
        }
        webSocketService.analyseMessage(onlineClient, message);
        logger.info(session.getRemoteAddress() + "发来了一条消息：" + message.bodyAsString());
    }

    @Override
    public void onClose(Session session) {
        OnlineClient onlineClient = onlineClientRepository.find(session.param("account"));
        if (onlineClient != null) {
            clientService.logout(onlineClient);
        }
        logger.info(session.getRemoteAddress() + "断开了连接");
    }

    @Override
    public void onError(Session session, Throwable error) {
        logger.err(session.getRemoteAddress() + "触发了一个错误：" + error.toString());
        error.printStackTrace();
    }

    void closeSession(Session session, String reason) {
        try {
            session.close();
            logger.warn("尝试关闭来自 " + session.getRemoteAddress() + " 的连接，理由：" + reason);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}