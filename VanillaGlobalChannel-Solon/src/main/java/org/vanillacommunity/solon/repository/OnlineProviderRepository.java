package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.provider.OnlineProvider;
import org.vanillacommunity.solon.entity.provider.Provider;


@Singleton(true)
@Component
public class OnlineProviderRepository extends Repository<String, OnlineProvider> {
    public OnlineProviderRepository() { super(Provider::getAccount); }
}
