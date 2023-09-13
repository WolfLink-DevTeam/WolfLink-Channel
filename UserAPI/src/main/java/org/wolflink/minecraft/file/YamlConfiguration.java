package org.wolflink.minecraft.file;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.wolflink.common.ioc.IOC;
import org.wolflink.minecraft.interfaces.ILogger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class YamlConfiguration {
    protected final YamlConfigurationLoader loader;
    protected CommentedConfigurationNode root = null;
    public YamlConfiguration(String path) {
        loader = YamlConfigurationLoader.builder()
                .path(Paths.get(path))
                .build();
    }
    protected ILogger getLogger() { return IOC.getBean(ILogger.class); }
    protected void loadRoot() {

        try {
            root = loader.load();
        } catch (IOException e) {
            getLogger().err("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }
    public void load(){}
    public void save(){}
}
