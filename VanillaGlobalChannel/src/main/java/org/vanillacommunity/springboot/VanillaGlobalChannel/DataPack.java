package org.vanillacommunity.springboot.VanillaGlobalChannel;

public class DataPack {

    private final int channelID;
    private final String serverDisplayName;
    private final String playerDisplayName;
    private String message;

    /**
     * 创建一个标准的消息数据包
     * @param channelID 频道ID
     * @param serverDisplayName 服务器名称
     * @param playerDisplayName 玩家名称
     * @param message 消息内容
     */
    public DataPack(int channelID,String serverDisplayName,String playerDisplayName,String message)
    {
        this.channelID = channelID;
        this.serverDisplayName = serverDisplayName;
        this.playerDisplayName = playerDisplayName;
        this.message = message;
    }
    @Override
    public String toString()
    {
        return channelID+"|"+serverDisplayName+"|"+playerDisplayName+"|"+message;
    }
    public static DataPack fromString(String dataStr)
    {
        int channelID;
        String serverDisplayName;
        String playerDisplayName;
        String message;

        int first = dataStr.indexOf("|");
        int second = dataStr.indexOf("|",first+1);
        int third = dataStr.indexOf("|",second+1);

        channelID = Integer.parseInt(dataStr.substring(0,first));
        serverDisplayName = dataStr.substring(first+1,second);
        playerDisplayName = dataStr.substring(second+1,third);
        message = dataStr.substring(third+1);

        return new DataPack(channelID,serverDisplayName,playerDisplayName,message);
    }

    public int getChannelID() {
        return channelID;
    }

    public String getMessage() {
        return message;
    }

    public String getPlayerDisplayName() {
        return playerDisplayName;
    }

    public String getServerDisplayName() {
        return serverDisplayName;
    }
}
