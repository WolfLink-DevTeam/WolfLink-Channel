package org.vanillacommunity.solon.config;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entityimpl.CommonChannel;
import org.vanillacommunity.solon.repository.ChannelRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton(true)
@Component
public class ChannelsConfig implements ILoadable {
    @Inject
    ChannelRepository channelRepository;

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
            channelRepository.update(new CommonChannel(Integer.parseInt(id), name, password, announcement));
        });
    }
}
