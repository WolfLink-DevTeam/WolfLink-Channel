package org.vanillacommunity.solon.config;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.SecureClient;
import org.vanillacommunity.solon.repository.SecureClientRepository;
import org.wolflink.minecraft.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton(true)
@Component
public class ClientsConfig implements ILoadable {
    @Inject
    SecureClientRepository secureClientRepository;

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
            List<String> ipSegments = solonApp.cfg().getList("clients." + account + ".ip_segments");
            SecureClient secureClient = new SecureClient(new Client(account,name,displayName), password, ipSegments);
            secureClient.setName(name);
            secureClient.setDisplayName(displayName);
            secureClientRepository.update(secureClient);
        });
    }
}
