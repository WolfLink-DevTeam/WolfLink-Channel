package org.vanillacommunity.solon.config;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.vanillacommunity.solon.entity.SecureChannel;
import org.vanillacommunity.solon.repository.SecureChannelRepository;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
@Component
public class ChannelsConfig implements ILoadable {
    @Inject
    SecureChannelRepository secureChannelRepository;

    @Override
    public void load(SolonApp solonApp) {
        Set<String> channelIds = new HashSet<>();
        solonApp.cfg().getMap("channels").forEach((k, v) -> {
            String[] nodes = k.split("\\.");
            channelIds.add(nodes[1]);
        });
        // foreach providers data
        channelIds.forEach(id -> {
            String name = solonApp.cfg().getProperty("channels." + id + ".name");
            String password = solonApp.cfg().getProperty("channels." + id + ".password");
            List<String> announcement = solonApp.cfg().getList("channels." + id + ".announcement");
            secureChannelRepository.update(new SecureChannel(Integer.parseInt(id), name, password, announcement));
        });
    }
}
