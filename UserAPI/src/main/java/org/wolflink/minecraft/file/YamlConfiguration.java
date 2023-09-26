package org.wolflink.minecraft.file;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.wolflink.common.ioc.IOC;
import org.wolflink.minecraft.BeanConfig;
import org.wolflink.minecraft.interfaces.ILogger;
import org.wolflink.minecraft.interfaces.PlatformAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class YamlConfiguration {
    protected YamlConfigurationLoader loader = null;
    protected CommentedConfigurationNode root = null;
    private final String fileName;
    public YamlConfiguration(String fileName) {
        this.fileName = fileName;
    }
    protected ILogger getLogger() { return IOC.getBean(ILogger.class); }
    protected void loadRoot() {
        try {
            File folder = IOC.getBean(PlatformAdapter.class).getDataFolder();
            File file = new File(folder,fileName+".yml");
            if(!file.exists()) file.createNewFile();
            loader = YamlConfigurationLoader.builder()
                    .file(file)
                    .build();
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
