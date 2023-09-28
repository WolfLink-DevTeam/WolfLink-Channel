package org.vanillacommunity.solon.service;

import com.google.gson.JsonElement;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.Logger;
import org.vanillacommunity.solon.entity.OnlineClient;
import org.vanillacommunity.solon.entity.SecureClient;
import org.vanillacommunity.solon.repository.OnlineClientRepository;
import org.vanillacommunity.solon.repository.SecureChannelRepository;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.GlobalMessage;
import org.wolflink.minecraft.MsgType;
import org.wolflink.minecraft.PlatformType;

import java.io.IOException;
import java.util.Date;

/**
 * 对于一个具体客户端的业务类
 */
@Singleton
@Component
public class ClientService {
    @Inject
    OnlineClientRepository onlineClientRepository;
    @Inject
    WebSocketService webSocketService;
    @Inject
    SecureChannelRepository secureChannelRepository;
    @Inject
    Logger logger;

    /**
     * 尝试登录
     * 登录过程会比对客户端传入的频道密码
     * 只有登录成功后客户端才会被添加到 OnlineClientRepository 当中
     * 可能会出现登录失败的情况，即频道密码不符
     *
     * @param secureClient 用户客户端
     * @param session      WebSocket连接对象
     * @param channelId    频道ID
     */
    public void login(SecureClient secureClient, Session session, int channelId) {
        String channelPassword = session.param("channel_password");
        String platformTypeStr = session.param("platform_type");
        PlatformType platformType;
        try {
            platformType = PlatformType.valueOf(platformTypeStr);
        } catch (Exception ignore) {
            logger.warn("未能获取到" + secureClient.getAccount() + "的平台标识：" + platformTypeStr);
            platformType = PlatformType.UNKNOWN;
        }
        if (channelPassword == null) channelPassword = "";
        if (!channelPassword.equals(secureChannelRepository.find(channelId).getPassword())) {
            logger.warn("客户端 " + secureClient.getAccount() + " 尝试登录频道 " + channelId + " 但密码错误，阻止本次连接。");
            try {
                // 关闭用户连接
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        OnlineClient onlineClient = new OnlineClient(secureClient, channelId, session, new Date(), platformType);
        onlineClientRepository.update(onlineClient);
        logger.info(secureClient.getAccount() + " 成功登录，所在频道 " + channelId);
    }

    /**
     * 登出客户端
     * 将给定的在线客户端从在线客户端列表中移除
     *
     * @param onlineClient 在线客户端对象
     */
    public void logout(OnlineClient onlineClient) {
        onlineClientRepository.delete(onlineClient.getAccount());
        logger.info(onlineClient.getAccount() + " 已离线，所在频道 " + onlineClient.getChannelId());
    }

    /**
     * 向指定的客户端发送系统消息
     *
     * @param onlineClient 在线客户端对象
     * @param jsonElement  将要发送的 JsonElement 对象
     */
    public void sendSystemMsg(OnlineClient onlineClient, JsonElement jsonElement) {
        onlineClient.getSession().sendAsync(
                webSocketService.formatData(MsgType.SYSTEM, jsonElement)
        );
    }

    /**
     * 向指定客户端发送公告消息
     *
     * @param onlineClient  在线客户端
     * @param globalMessage 消息对象
     */
    public void sendAnnouncementMsg(OnlineClient onlineClient, GlobalMessage globalMessage) {
        onlineClient.getSession().sendAsync(
                webSocketService.formatData(MsgType.ANNOUNCEMENT, globalMessage)
        );
    }

    /**
     * 向指定客户端发送频道消息
     *
     * @param onlineClient  在线客户端
     * @param globalMessage 消息对象
     */
    public void sendChannelMsg(OnlineClient onlineClient, GlobalMessage globalMessage) {
        onlineClient.getSession().sendAsync(
                webSocketService.formatData(MsgType.CHANNEL, globalMessage)
        );
    }

}
