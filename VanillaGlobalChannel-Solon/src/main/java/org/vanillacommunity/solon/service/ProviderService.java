package org.vanillacommunity.solon.service;

import lombok.NonNull;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.message.Session;
import org.vanillacommunity.solon.entity.provider.OnlineProvider;
import org.vanillacommunity.solon.entity.provider.Provider;
import org.vanillacommunity.solon.repository.ProviderRepository;

import java.util.Date;

@Singleton(true)
@Component
public class ProviderService {
    @Inject
    ProviderRepository providerRepository;

    public void login(Session session) {
        @NonNull
        String account = session.param("account");
        @NonNull
        String token = session.param("token");
        int channelId = Integer.parseInt(session.param("channel_id"));
        providerRepository.update(new OnlineProvider(account, token, channelId, session, new Date()));
    }

    public void logout(Session session) {
        @NonNull
        String account = session.param("account");
        @NonNull
        String token = session.param("token");
        providerRepository.update(new Provider(account, token));
    }

}
