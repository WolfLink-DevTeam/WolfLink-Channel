package org.vanillacommunity.solon.service;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.repository.OnlineProviderRepository;

/**
 * 消息服务
 * 向指定频道发送广播消息
 * 向所有 Provider 发送全体消息
 */
@Singleton(true)
@Component
public class MessageService {
    @Inject
    OnlineProviderRepository onlineProviderRepository;
    public void broadcast(int channelId,String text) {

    }
    public void broadcast(String text) {

    }
}
