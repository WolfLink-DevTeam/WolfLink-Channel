package org.vanillacommunity.solon.entity;

import lombok.Getter;
import org.wolflink.minecraft.Channel;
import org.wolflink.minecraft.Client;
import org.wolflink.minecraft.MessageContainer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 私密频道对象
 * 包含频道的密码信息
 */
@Getter
public class SecureChannel extends Channel {
    // 进入频道需要的口令
    private String password;
    // 当前频道的在线客户端
    private final Set<OnlineClient> onlineClients = new HashSet<>();

    public SecureChannel(int id, String name, List<String> announcement) {
        super(id, name, announcement,new MessageContainer());
        this.password = "";
    }

    public SecureChannel(int id, String name, String password, List<String> announcement) {
        super(id, name, announcement,new MessageContainer());
        this.password = password;
    }
    public Channel toChannel() {
        return new Channel(this);
    }
}
