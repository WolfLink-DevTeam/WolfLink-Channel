package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entityimpl.OnlineClient;
import org.vanillacommunity.solon.entity.Client;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * 在线客户端仓库
 * 管理当前与中央服务器建立连接的在线客户端
 */
@Singleton(true)
@Component
public class OnlineClientRepository extends Repository<String, OnlineClient> {
    public OnlineClientRepository() {
        super(Client::getAccount);
    }

    public Set<OnlineClient> filterByChannelId(int channelId) {
        return findAll().stream().filter(client -> client.getChannelId() == channelId).collect(Collectors.toSet());
    }
}
