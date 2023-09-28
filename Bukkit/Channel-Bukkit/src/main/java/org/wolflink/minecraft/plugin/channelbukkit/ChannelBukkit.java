package org.wolflink.minecraft.plugin.channelbukkit;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.wolflink.common.ioc.IOC;
import org.wolflink.minecraft.Application;
import org.wolflink.minecraft.plugin.channelbukkit.impl.BeanConfig;

import java.util.Objects;

@Getter
public final class ChannelBukkit extends JavaPlugin {

    private Application channelApp;
    @Override
    public void onEnable() {
        // Plugin startup logic
        channelApp = new Application(new BeanConfig(this));
        channelApp.setEnabled(true);
        Objects.requireNonNull(Bukkit.getPluginCommand("channel"))
                .setExecutor(IOC.getBean(CommandManager.class));
        Bukkit.getPluginManager().registerEvents(new ChatListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        channelApp.setEnabled(false);
    }
}
