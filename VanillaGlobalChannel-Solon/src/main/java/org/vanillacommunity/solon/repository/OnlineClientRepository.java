package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.entity.client.OnlineClient;
import org.vanillacommunity.solon.entity.client.Client;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Singleton(true)
@Component
public class OnlineClientRepository extends Repository<String, OnlineClient> {
    public OnlineClientRepository() { super(Client::getAccount); }
    public Set<OnlineClient> filterByChannelId(int channelId) {
        return findAll().stream().filter(client -> client.getChannelId() == channelId).collect(Collectors.toSet());
    }
}
