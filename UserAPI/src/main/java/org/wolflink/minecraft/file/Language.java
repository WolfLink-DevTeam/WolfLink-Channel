package org.wolflink.minecraft.file;

import lombok.Getter;
import org.wolflink.common.ioc.Singleton;

@Getter
@Singleton
public class Language extends YamlConfiguration {
    private String prefix;
    private String cmdHelp;
    private String channelLeave;
    private String channelJoin;
    private String serverOnline;
    private String serverOffline;
    private String chatTemplate = "%sender% » %content%";
    private String announcementTemplate = "[ 全球公告 ] %content%";

    public Language() {
        // TODO 改为 PlatformAdapter 提供的数据文件夹路径
        super("GlobalChannel/lang.yml");
    }

    public void load() {
        loadRoot();
        if(root == null) return;
    }
}
