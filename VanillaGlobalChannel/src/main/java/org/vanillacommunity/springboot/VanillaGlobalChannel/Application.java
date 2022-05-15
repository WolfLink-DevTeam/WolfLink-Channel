package org.vanillacommunity.springboot.VanillaGlobalChannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration("application")
public class Application {

    public Application(@Autowired MCServerManager mcServerManager, @Autowired ChannelManager channelManager)
    {
        mcServerManager.init();
        channelManager.init();
    }

}
