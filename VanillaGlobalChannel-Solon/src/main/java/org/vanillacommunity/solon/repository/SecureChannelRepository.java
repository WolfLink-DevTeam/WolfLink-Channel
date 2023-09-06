package org.vanillacommunity.solon.repository;

import org.noear.solon.annotation.Component;
import org.vanillacommunity.solon.entity.SecureChannel;
import org.wolflink.common.ioc.Singleton;

/**
 * 频道仓库
 */
@Singleton
@Component
public class SecureChannelRepository extends Repository<Integer, SecureChannel> {
    public SecureChannelRepository() {
        super(SecureChannel::getId);
    }
}
