package org.vanillacommunity.vanillaglobalchannel.nukkit;

import cn.nukkit.plugin.PluginBase;
import org.vanillacommunity.vanillaglobalchannel.common.Main;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;
import org.vanillacommunity.vanillaglobalchannel.common.PlatformSign;
import org.vanillacommunity.vanillaglobalchannel.nukkit.command.MainCommand;
import org.vanillacommunity.vanillaglobalchannel.nukkit.listener.OnChatEvent;
import org.vanillacommunity.vanillaglobalchannel.nukkit.listener.OnLeaveEvent;
import org.vanillacommunity.vanillaglobalchannel.nukkit.listener.OnLoginEvent;

public final class Nukkit extends PluginBase {

    private static PluginBase instance;

    public static PluginBase getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        PlatformAdapter.platformSign = PlatformSign.NUKKIT;
        instance = this;
        // Plugin startup logic
        Main.getInstance().onInit();

        Main.getInstance().onStart();

        this.getServer().getCommandMap().register("", new MainCommand("vgc"));

        this.getServer().getPluginManager().registerEvents(new OnChatEvent(),this);
        this.getServer().getPluginManager().registerEvents(new OnLeaveEvent(),this);
        this.getServer().getPluginManager().registerEvents(new OnLoginEvent(),this);
    }
}
