package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.vanillacommunity.solon.entity.SecureChannel;

/**
 * 频道仓库
 */
@Singleton(true)
@Component
public class SecureChannelRepository extends Repository<Integer, SecureChannel> {
    public SecureChannelRepository() {
        super(SecureChannel::getId);
    }
}
