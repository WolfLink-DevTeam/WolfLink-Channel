package org.vanillacommunity.solon.entity.channel;

import org.vanillacommunity.solon.api.enums.ChannelType;

import java.util.List;
import java.util.Set;

public class PrivateChannel extends Channel {
    // 私有频道密钥
    String password;
    public PrivateChannel(int id, String name, List<String> announcement,String password) {
        super(id, name, ChannelType.PRIVATE, announcement);
        this.password = password;
    }
}
