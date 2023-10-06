package org.wolflink.minecraft;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class Channel {
    // 频道ID 必须保证独一无二
    private final int id;
    // 频道名称
    private final String name;
    // 频道置顶公告
    private final List<String> announcement;
    // 消息容器，存储消息历史记录
    private final MessageContainer messageContainer;
    public Channel(Channel channel) {
        this.id = channel.id;
        this.name = channel.name;
        this.announcement = channel.announcement;
        this.messageContainer = channel.messageContainer;
    }
}
