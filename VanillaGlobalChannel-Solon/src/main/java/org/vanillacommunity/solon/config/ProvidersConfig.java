package org.vanillacommunity.solon.config;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.api.enums.PlatformType;
import org.vanillacommunity.solon.entity.provider.Provider;
import org.vanillacommunity.solon.repository.ProviderRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton(true)
@Component
public class ProvidersConfig {
    public void load(SolonApp application) {
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
            Set<PlatformType> allowedPlatforms = application.cfg().getList("providers."+account+".allowed_platforms")
                    .stream()
                    .map(it -> {
                        try {
                            return PlatformType.valueOf(it.toUpperCase());
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .collect(Collectors.toSet());
            providerRepository.update(new Provider(account,token,allowedPlatforms));
        });
    }
}
