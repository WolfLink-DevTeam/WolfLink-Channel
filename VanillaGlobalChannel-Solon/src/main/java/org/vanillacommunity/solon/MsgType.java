package org.vanillacommunity.solon;

/**
 * 消息类型
 */
public enum MsgType {
    /**
     * 系统消息，与插件端进行必要的数据交互，玩家不可见
     */
    SYSTEM,
    /**
     * 正常频道内消息，玩家可见
     */
    CHANNEL,
    /**
     * 来自中央服务端的公告消息，玩家可见
     */
    ANNOUNCEMENT
}
