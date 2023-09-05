package org.vanillacommunity.solon.entityimpl;

import lombok.Getter;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.entity.SecureClient;
import org.wolflink.minecraft.Client;
import org.wolflink.minecraft.PlatformType;

import java.util.Date;

/**
 * 在线的消息提供者对象
 */
public class OnlineClient extends Client {
    @Getter
    // 连接信息
    Session session;
    @Getter
    // 建立连接的时间
    Date onlineTime;
    // 当前所处频道ID
    @Getter
    int channelId;
    // 客户端类型
    PlatformType platformType;

    public OnlineClient(Client client, int channelId, Session session, Date onlineTime,PlatformType platformType) {
        super(client.getAccount(),client.getName(),client.getDisplayName());
        this.channelId = channelId;
        this.session = session;
        this.onlineTime = onlineTime;
        this.platformType = platformType;
    }
}
