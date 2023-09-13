package org.wolflink.minecraft.plugin.channelbukkit.impl;

import org.bukkit.Bukkit;
import org.wolflink.minecraft.interfaces.ILogger;

public class Logger implements ILogger {
    @Override
    public void info(String msg) {
        Bukkit.getLogger().info(msg);
    }

    @Override
    public void warn(String msg) {
        Bukkit.getLogger().warning(msg);
    }

    @Override
    public void err(String msg) {
        Bukkit.getLogger().severe(msg);
    }
}
