package org.vanillacommunity.solon.repository;


import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.provider.Provider;


@Singleton(true)
@Component
public class ProviderRepository extends Repository<String, Provider>{
    public ProviderRepository() {
        super(Provider::getAccount);
    }
}
