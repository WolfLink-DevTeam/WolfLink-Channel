package org.vanillacommunity.solon.config;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.IOC;
import org.vanillacommunity.solon.entity.provider.Provider;
import org.vanillacommunity.solon.repository.ProviderRepository;

import java.util.HashSet;
import java.util.Set;

@Singleton(true)
@Component
public class ProvidersConfig implements ILoadable{
    public void load() {
        SolonApp application = IOC.get(SolonApp.class);
        // load provider accounts
        Set<String> providerAccounts = new HashSet<>();
        application.cfg().getMap("providers").forEach((k,v)->{
            String[] nodes =  k.split("\\.");
            providerAccounts.add(nodes[1]);
        });
        // foreach providers data
        ProviderRepository providerRepository = application.context().getBean(ProviderRepository.class);
        providerAccounts.forEach(account -> {
            String token = application.cfg().getProperty("providers."+account+".token");
            providerRepository.update(new Provider(account,token));
        });
    }
}
