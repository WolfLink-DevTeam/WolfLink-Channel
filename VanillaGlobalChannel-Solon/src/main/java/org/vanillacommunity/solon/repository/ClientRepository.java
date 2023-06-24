package org.vanillacommunity.solon.repository;


import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.client.Client;


@Singleton(true)
@Component
public class ClientRepository extends Repository<String, Client>{
    public ClientRepository() {
        super(Client::getAccount);
    }
}
