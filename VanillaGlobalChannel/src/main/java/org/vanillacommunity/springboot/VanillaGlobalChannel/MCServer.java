package org.vanillacommunity.springboot.VanillaGlobalChannel;

public class MCServer {

    int id;
    String displayName;
    String color;

    /**
     * 一个Minecraft服务器对象
     * @param displayName  该服务器的展示名称
     */
    public MCServer(int id,String displayName,String color)
    {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
    }
}
