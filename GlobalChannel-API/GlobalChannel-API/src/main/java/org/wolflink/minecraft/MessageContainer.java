package org.wolflink.minecraft;

import org.wolflink.minecraft.GlobalMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息容器，应该归属一个指定的频道
 * 目前只支持保存运行期间的消息记录
 * 后端服务器重启则消息记录清空
 * 该容器也承载了自动发送相应频道消息的功能
 */
public class MessageContainer {
    /**
     * 消息历史记录，索引越小则代表消息越早发送
     */
    private final List<GlobalMessage> history = new ArrayList<>();

    public void add(GlobalMessage globalMessage) {
        history.add(globalMessage);
    }

    /**
     * 查看最近发送的消息
     *
     * @param count 指定查看的消息数量
     * @return List索引越小的消息越早
     */
    public List<GlobalMessage> lookup(int count) {
        int length = history.size();
        if (count > length) count = length;
        return history.subList(length - count, length);
    }

}
