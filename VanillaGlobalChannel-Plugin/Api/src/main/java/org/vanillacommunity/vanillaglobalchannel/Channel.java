package org.vanillacommunity.vanillaglobalchannel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class Channel {
    // 频道ID 必须保证独一无二
    private final int id;
    // 频道名称
    private final String name;
    // 频道口令
    private final String password;
    // 频道置顶公告
    private final List<String> announcement;
}
