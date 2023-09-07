package org.vanillacommunity.vanillaglobalchannel.common.network;

import lombok.Getter;

/**
 * 一个并不通用的数据包封装
 * 这样的封装没有意义...
 */
@Getter
public class DataPack {

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
    public DataPack(int channelID, int serverID, String playerDisplayName, String message)
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
    public static DataPack fromString(String dataStr)
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

        return new DataPack(channelID,Integer.parseInt(serverID),playerDisplayName,message);
    }
}
