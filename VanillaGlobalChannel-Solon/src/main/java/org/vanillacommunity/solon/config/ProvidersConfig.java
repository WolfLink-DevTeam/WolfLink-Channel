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
public class ProvidersConfig implements ILoadable{
    @Inject
    ClientRepository clientRepository;
    public void load(SolonApp solonApp) {
        // load provider accounts
        Set<String> providerAccounts = new HashSet<>();
        solonApp.cfg().getMap("clients").forEach((k,v)->{
            String[] nodes =  k.split("\\.");
            providerAccounts.add(nodes[1]);
        });
        // foreach providers data
        providerAccounts.forEach(account -> {
            String token = solonApp.cfg().getProperty("clients."+account+".token");
            clientRepository.update(new Client(account,token));
        });
    }
}
