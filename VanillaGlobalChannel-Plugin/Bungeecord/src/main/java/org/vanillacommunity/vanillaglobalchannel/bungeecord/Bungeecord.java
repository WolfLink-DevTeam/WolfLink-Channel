package org.vanillacommunity.vanillaglobalchannel.bungeecord;

import lombok.Data;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import org.vanillacommunity.vanillaglobalchannel.bungeecord.command.MainCommand;
import org.vanillacommunity.vanillaglobalchannel.bungeecord.listener.OnChatEvent;
import org.vanillacommunity.vanillaglobalchannel.bungeecord.listener.OnLeaveEvent;
import org.vanillacommunity.vanillaglobalchannel.bungeecord.listener.OnLoginEvent;
import org.vanillacommunity.vanillaglobalchannel.common.Main;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformSign;

public final class Bungeecord extends Plugin {

    private static Plugin instance;

    public static Plugin getInstance()
    {
        return instance;
    }
    @Override
    public void onEnable() {
        PlatformAdapter.platformSign = PlatformSign.BUNGEECORD;
        instance = this;
        // Plugin startup logic
        Main.getInstance().onInit();

        Main.getInstance().onStart();

        this.getProxy().getPluginManager().registerCommand(this,new MainCommand());

        this.getProxy().getPluginManager().registerListener(this,new OnChatEvent());
        this.getProxy().getPluginManager().registerListener(this,new OnLeaveEvent());
        this.getProxy().getPluginManager().registerListener(this,new OnLoginEvent());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
