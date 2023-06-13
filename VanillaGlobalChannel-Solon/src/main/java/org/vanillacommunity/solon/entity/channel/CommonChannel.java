package org.vanillacommunity.solon.entity.channel;

import org.vanillacommunity.solon.api.enums.ChannelType;
import org.vanillacommunity.solon.api.enums.PlatformType;

import java.util.List;
import java.util.Set;

public class CommonChannel extends Channel {
    public CommonChannel(int id, String name, List<String> announcement, Set<PlatformType> allowedPlatforms) {
        super(id, name, ChannelType.COMMON, announcement, allowedPlatforms);
    }
}
