package org.vanillacommunity.solon.entity.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.vanillacommunity.solon.api.enums.ChannelType;
import org.vanillacommunity.solon.api.enums.PlatformType;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public abstract class Channel {
    // 频道ID 必须保证独一无二
    private final int id;
    // 频道名称
    private final String name;
    // 频道类型
    private final ChannelType channelType;
    // 频道置顶公告
    private final List<String> announcement;
    // 允许加入的平台标识
    private final Set<PlatformType> allowedPlatforms;
}
