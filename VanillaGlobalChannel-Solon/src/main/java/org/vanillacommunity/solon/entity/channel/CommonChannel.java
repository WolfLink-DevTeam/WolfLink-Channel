package org.vanillacommunity.solon.entity.channel;

import java.util.List;

public class CommonChannel extends Channel {
    public CommonChannel(int id, String name,String password, List<String> announcement) {
        super(id, name, password, announcement);
    }
}