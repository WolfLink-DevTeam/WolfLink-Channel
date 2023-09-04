package org.vanillacommunity.solon.entityimpl;

import org.vanillacommunity.solon.entity.Channel;
import org.vanillacommunity.solon.entity.MessageContainer;

import java.util.List;

public class CommonChannel extends Channel {
    public CommonChannel(int id, String name, String password, List<String> announcement) {
        super(id, name, password, announcement, new MessageContainer());
    }
}
