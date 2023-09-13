package org.wolflink.minecraft.plugin.channelbukkit;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.wolflink.minecraft.Application;
import org.wolflink.minecraft.plugin.channelbukkit.impl.BeanConfig;

@Getter
public final class ChannelBukkit extends JavaPlugin {

    private Application channelApp;
    @Override
    public void onEnable() {
        // Plugin startup logic
        channelApp = new Application(new BeanConfig());
        channelApp.setEnabled(true);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        channelApp.setEnabled(false);
    }
}
