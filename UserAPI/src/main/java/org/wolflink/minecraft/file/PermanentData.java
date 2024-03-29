package org.wolflink.minecraft.file;

import lombok.Getter;
import org.wolflink.common.ioc.Singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
@Getter
public class PermanentData extends YamlConfiguration {
    private Set<String> channelPlayers = new HashSet<>();

    public PermanentData() {
        super("permanent");
    }

    public void load() {
        loadRoot();
        try {
            channelPlayers = new HashSet<>(root.node("ChannelPlayers").getList(String.class, new ArrayList<>()));
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().err("Permanent.yml 文件加载出现异常。");
        }
    }
    public void save() {
        try {
            root.node("ChannelPlayers").setList(String.class,new ArrayList<>(channelPlayers));
            loader.save(root);
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().err("Permanent.yml 文件保存出现异常。");
        }

    }
}
