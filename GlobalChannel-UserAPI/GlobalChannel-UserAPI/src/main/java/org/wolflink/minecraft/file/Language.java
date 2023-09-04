package org.wolflink.minecraft.file;

import lombok.Getter;
import org.wolflink.common.ioc.Singleton;

@Getter
@Singleton
public class Language {
    private String prefix;
    private String cmdHelp;
    private String channelLeave;
    private String channelJoin;
    private String serverOnline;
    private String serverOffline;
    private String chatTemplate = "%sender% » %content%";
    private String announcementTemplate = "[ 全球公告 ] %content%";
    public void load() {

    }
}
