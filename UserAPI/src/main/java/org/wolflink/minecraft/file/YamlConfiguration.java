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
import java.util.ArrayList;

public abstract class YamlConfiguration {
    protected YamlConfigurationLoader loader = null;
    protected CommentedConfigurationNode root = null;
    private final String fileName;
    public YamlConfiguration(String fileName) {
        this.fileName = fileName;
    }
    protected ILogger getLogger() { return IOC.getBean(ILogger.class); }

    public File getFile() {
        File folder = IOC.getBean(PlatformAdapter.class).getDataFolder();
        if(!folder.exists())folder.mkdirs();
        File file = new File(folder,fileName+".yml");
        try {
            if(!file.exists()) file.createNewFile();
        } catch (IOException e) {
            getLogger().err("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
        return file;
    }
    protected void loadRoot() {
        try {
            File file = getFile();
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
        if(root == null || root.isNull()) {
            getLogger().warn("配置文件未初始化，系统将采用默认配置");
        }
    }
    public void load(){}
    public void save(){
        try {
            loader.save(root);
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().err(fileName+".yml 文件保存出现异常。");
        }
    }
}
