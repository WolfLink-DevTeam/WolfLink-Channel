package org.vanillacommunity.vanillaglobalchannel.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Data;
import org.slf4j.Logger;
import org.vanillacommunity.vanillaglobalchannel.common.Main;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformSign;
import org.vanillacommunity.vanillaglobalchannel.velocity.command.MainCommand;
import org.vanillacommunity.vanillaglobalchannel.velocity.listener.OnChatEvent;
import org.vanillacommunity.vanillaglobalchannel.velocity.listener.OnLeaveEvent;
import org.vanillacommunity.vanillaglobalchannel.velocity.listener.OnLoginEvent;

import java.nio.file.Path;

@Plugin(
        id = "velocityglobalchannel",
        name = "Velocity",
        version = "1.0-SNAPSHOT",
        authors = {"MikkoAyaka"}
)
@Data
public class Velocity {

    private static Velocity instance;

    private final ProxyServer server;
    private final Logger logger;
    private final Path path;

    @Inject
    public Velocity(ProxyServer server,Logger logger,@DataDirectory Path path) {

        PlatformAdapter.platformSign = PlatformSign.VELOCITY;

        this.server = server;
        this.logger = logger;
        this.path = path;

        instance = this;

        CommandMeta commandMeta = server.getCommandManager().metaBuilder("vgc").build();
        server.getCommandManager().register(commandMeta,new MainCommand());
        Main.getInstance().onInit();
    }
    public static Velocity getInstance() { return instance; }


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Main.getInstance().onStart();

        Velocity.getInstance().getServer().getEventManager().register(this,new OnChatEvent());
        Velocity.getInstance().getServer().getEventManager().register(this,new OnLoginEvent());
        Velocity.getInstance().getServer().getEventManager().register(this,new OnLeaveEvent());
    }
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event){
        Main.getInstance().onShutdown();
    }
}
