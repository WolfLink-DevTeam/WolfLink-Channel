package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.vanillacommunity.solon.entity.OnlineClient;
import org.wolflink.common.ioc.Singleton;

import java.util.Set;
import java.util.stream.Collectors;


/**
 * 在线客户端仓库
 * 管理当前与中央服务器建立连接的在线客户端
 */
@Singleton
@Component
public class OnlineClientRepository extends Repository<String, OnlineClient> {
    public OnlineClientRepository() {
        super(OnlineClient::getAccount);
    }

    public Set<OnlineClient> filterByChannelId(int channelId) {
        return findAll().stream().filter(client -> client.getChannelId() == channelId).collect(Collectors.toSet());
    }
}
