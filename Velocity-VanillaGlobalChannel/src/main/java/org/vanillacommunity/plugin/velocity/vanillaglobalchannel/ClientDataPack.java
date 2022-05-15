package org.vanillacommunity.plugin.velocity.vanillaglobalchannel;

import lombok.Getter;

@Getter
public class ClientDataPack {

    private final int channelID;
    private final int serverID;
    private final String playerDisplayName;
    private String message;

    /**
     * 创建一个标准的消息数据包
     * @param channelID 频道ID
     * @param serverID 服务器ID
     * @param playerDisplayName 玩家名称
     * @param message 消息内容
     */
    public ClientDataPack(int channelID, int serverID, String playerDisplayName, String message)
    {
        this.channelID = channelID;
        this.serverID = serverID;
        this.playerDisplayName = playerDisplayName;
        this.message = message;
    }
    @Override
    public String toString()
    {
        return channelID+"|"+serverID+"|"+playerDisplayName+"|"+message;
    }
    public static ClientDataPack fromString(String dataStr)
    {
        int channelID;
        String serverID;
        String playerDisplayName;
        String message;

        int first = dataStr.indexOf("|");
        int second = dataStr.indexOf("|",first+1);
        int third = dataStr.indexOf("|",second+1);

        channelID = Integer.parseInt(dataStr.substring(0,first));
        serverID = dataStr.substring(first+1,second);
        playerDisplayName = dataStr.substring(second+1,third);
        message = dataStr.substring(third+1);

        return new ClientDataPack(channelID,Integer.parseInt(serverID),playerDisplayName,message);
    }
}
