package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.client.OnlineClient;
import org.vanillacommunity.solon.entity.client.Client;


@Singleton(true)
@Component
public class OnlineClientRepository extends Repository<String, OnlineClient> {
    public OnlineClientRepository() { super(Client::getAccount); }
}
