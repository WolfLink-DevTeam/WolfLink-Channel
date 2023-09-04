package org.vanillacommunity.solon.entityimpl;

import lombok.Getter;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.entity.Client;

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

    public OnlineClient(Client client, int channelId, Session session, Date onlineTime) {
        super(client.getAccount(), client.getPassword(), client.getIpSegments());
        this.channelId = channelId;
        this.session = session;
        this.onlineTime = onlineTime;
    }
}
