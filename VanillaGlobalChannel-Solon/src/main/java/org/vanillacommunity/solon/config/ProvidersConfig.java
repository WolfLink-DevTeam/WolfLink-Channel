package org.vanillacommunity.solon.config;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.provider.Provider;
import org.vanillacommunity.solon.repository.ProviderRepository;

import java.util.HashSet;
import java.util.Set;

@Singleton(true)
@Component
public class ProvidersConfig implements ILoadable{
    @Inject
    ProviderRepository providerRepository;
    public void load(SolonApp solonApp) {
        // load provider accounts
        Set<String> providerAccounts = new HashSet<>();
        solonApp.cfg().getMap("providers").forEach((k,v)->{
            String[] nodes =  k.split("\\.");
            providerAccounts.add(nodes[1]);
        });
        // foreach providers data
        providerAccounts.forEach(account -> {
            String token = solonApp.cfg().getProperty("providers."+account+".token");
            providerRepository.update(new Provider(account,token));
        });
    }
}
