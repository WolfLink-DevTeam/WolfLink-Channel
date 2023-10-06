package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.vanillacommunity.solon.entity.SecureChannel;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Channel;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 频道仓库
 */
@Singleton
@Component
public class SecureChannelRepository extends Repository<Integer, SecureChannel> {
    public SecureChannelRepository() {
        super(SecureChannel::getId);
    }
    public Set<Channel> findAllChannels() {
        return findAll().stream().map(SecureChannel::toChannel).collect(Collectors.toSet());
    }
}
