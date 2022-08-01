package org.vanillacommunity.vanillaglobalchannel.common.mcserver;

import lombok.Data;

@Data
public class MCServer {

    private final int id;
    private final String displayName;

    /**
     * 一个Minecraft服务器对象
     * @param displayName  该服务器的展示名称
     */
    public MCServer(int id,String displayName)
    {
        this.id = id;
        this.displayName = displayName;
    }
}
