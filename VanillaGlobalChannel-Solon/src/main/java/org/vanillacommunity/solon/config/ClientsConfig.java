package org.vanillacommunity.solon.config;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.client.Client;
import org.vanillacommunity.solon.repository.ClientRepository;

import java.util.HashSet;
import java.util.Set;

@Singleton(true)
@Component
public class ClientsConfig implements ILoadable {
    @Inject
    ClientRepository clientRepository;

    public void load(SolonApp solonApp) {
        // load provider accounts
        Set<String> clientAccounts = new HashSet<>();
        solonApp.cfg().getMap("clients").forEach((k, v) -> {
            String[] nodes = k.split("\\.");
            clientAccounts.add(nodes[1]);
        });
        // foreach providers data
        clientAccounts.forEach(account -> {
            String password = solonApp.cfg().getProperty("clients." + account + ".password");
            String name = solonApp.cfg().getProperty("clients." + account + ".name");
            String displayName = solonApp.cfg().getProperty("clients." + account + ".display_name");
            Client client = new Client(account, password);
            client.setName(name);
            client.setDisplayName(displayName);
            clientRepository.update(client);
        });
    }
}
