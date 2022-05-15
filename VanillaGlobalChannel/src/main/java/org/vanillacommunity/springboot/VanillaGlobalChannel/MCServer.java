package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Getter;

@Getter
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
