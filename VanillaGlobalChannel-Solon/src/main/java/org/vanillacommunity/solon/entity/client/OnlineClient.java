package org.vanillacommunity.solon.entity.client;

import lombok.Data;
import lombok.Getter;
import org.noear.solon.core.message.Session;

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
    @Getter
    int channelId;

    public OnlineClient(String account, String token, int channelId, Session session, Date onlineTime) {
        super(account, token);
        this.channelId = channelId;
        this.session = session;
        this.onlineTime = onlineTime;
    }
}
