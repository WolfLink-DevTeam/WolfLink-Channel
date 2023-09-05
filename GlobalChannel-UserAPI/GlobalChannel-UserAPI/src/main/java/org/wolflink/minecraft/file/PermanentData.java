package org.wolflink.minecraft.file;

import lombok.Getter;
import org.wolflink.common.ioc.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
@Getter
public class PermanentData extends YamlConfiguration {
    private List<String> channelPlayers;

    public PermanentData() {
        super("GlobalChannel/permanent.yml");
    }

    public void load() {
        loadRoot();
        if(root == null) return;
        try {
            channelPlayers = root.node("ChannelPlayers").getList(String.class,new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().err("Permanent.yml 文件加载出现异常。");
        }
    }
    public void save() {
        try {
            root.node("ChannelPlayers").setList(String.class,channelPlayers);
            loader.save(root);
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().err("Permanent.yml 文件保存出现异常。");
        }

    }
}
