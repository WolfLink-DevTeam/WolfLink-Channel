package org.vanillacommunity.solon.entity.channel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.vanillacommunity.solon.entity.message.MessageContainer;

import java.util.List;

/**
 * 抽象频道对象
 * 目前只有 CommonChannel 一个实现
 */
@Data
@AllArgsConstructor
public abstract class Channel {
    // 频道ID 必须保证独一无二
    private final int id;
    // 频道名称
    private final String name;
    // 进入频道需要的口令
    private final String password;
    // 频道置顶公告
    private final List<String> announcement;
    // 消息容器，存储消息历史记录
    private final MessageContainer messageContainer;
}
