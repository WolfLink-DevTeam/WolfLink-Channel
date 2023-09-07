package org.vanillacommunity.vanillaglobalchannel.common.channel;

import lombok.Getter;
import org.vanillacommunity.vanillaglobalchannel.common.*;
import org.vanillacommunity.vanillaglobalchannel.common.network.DataPack;
import org.vanillacommunity.vanillaglobalchannel.common.network.NetTransManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

import java.util.*;

/**
 * 频道管理者(职责太重，应该分散)
 * 负责管理频道中的玩家和不处于频道中的玩家
 * 还负责管理提示消息
 */
public class ChannelManager {
    // 单例实例(Common模块没有引入IOC)
    private static ChannelManager instance;
    // 频道用户信息，Key-频道ID Value-当前服务器处于该频道内的玩家
    private final Map<Integer,List<UUID>> channelUserMap = new HashMap<>();
    // 未处于频道中的玩家
    @Getter
    private final List<UUID> leftUserList = new ArrayList<>();
    // 频道发送消息的计时器(以一定周期发送dataPackQueue中的数据包)
    public Timer timer;
    // 向玩家们展示提示信息的计时器
    public Timer showTipsTimer;
    // Key-频道ID Value-频道对象
    private final Map<Integer,Channel> channelMap = new HashMap<>();
    // 消息队列
    private final Queue<DataPack> dataPackQueue = new LinkedList<>();
    // 无关紧要的提示信息
    boolean showTips = false;

    public Map<Integer, Channel> getChannelMap() {
        return channelMap;
    }

    public static ChannelManager getInstance()
    {
        if(instance == null)instance = new ChannelManager();
        return instance;
    }
    // 把数据包压入 dataPackQueue 消息队列中，之后会自动被发送到服务端
    public void offerPack(DataPack dataPack)
    {
        dataPackQueue.offer(dataPack);
        if(dataPackQueue.size() > 10) dataPackQueue.poll();
    }
    // 初始化频道
    public void initChannel(){
        System.out.println(ConfigManager.pluginStart);
        if(NetTransManager.getInstance().getWebSocket() != null)
        {
            // 从服务端获取多个频道的消息
            lookupChannelsInfo();
        }
        else
        {
            System.out.println(ConfigManager.cmdPrefix+"中央服务器连接失败！插件将不能正常运行。");
        }
    }
    // 改变玩家的频道
    public void changeChannel(UUID uuid,int oldChannelID,int newChannelID)
    {
        if(oldChannelID != -1)
        {
            leftUserList.remove(uuid);
            channelUserMap.get(oldChannelID).remove(uuid);
        }
        if(newChannelID != -1) channelUserMap.get(newChannelID).add(uuid);
        else if(!leftUserList.contains(uuid)) leftUserList.add(uuid);
    }
    public void lookupChannelsInfo()
    {
        NetTransManager.getInstance().getWebSocket().send("GetChannelInfo");
    }
    // 从字符串中解析频道数据创建频道对象
    public void bindChannelsInfo(String str)
    {
        List<String> channelsInfo = Tools.stringToList(str);

        for(String channelStr : channelsInfo)
        {
            String[] args = channelStr.split("/");

            Channel channel = new Channel(Integer.parseInt(args[0]),args[1],args[2]);
            // 把创建好的频道保存到 ChannelManager 中
            ChannelManager.getInstance().getChannelMap().put(channel.getId(),channel);
        }
        System.out.println(ConfigManager.pluginStart1);
        initChannelUserList();
        System.out.println(ConfigManager.pluginStart2);
        initChannelQueue();
        System.out.println(ConfigManager.pluginStart3);
        System.out.println(ConfigManager.pluginStartFinish);
    }
    // 初始化频道的用户列表
    private void initChannelUserList()
    {
        for(int channelID : channelMap.keySet())
        {
            channelUserMap.put(channelID,new ArrayList<>());
        }
    }
    // 初始化频道的消息队列，启用定时器以固定的周期发送队列中的数据包
    private void initChannelQueue()
    {
        if(timer == null)
        {
            timer = new Timer();
            // 启用定时器
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 尝试发送缓存消息队列的数据包
                    if(!dataPackQueue.isEmpty())
                    {
                        DataPack dataPack = dataPackQueue.peek();
                        if(dataPack == null)return;
                        if(NetTransManager.getInstance().sendChatData(dataPack))
                        {
                            //发送成功才出栈
                            dataPackQueue.poll();
                        }
                    }
                }
            },100,100);
        }
    }
    private void resetShowTipsTimer()
    {
        if(showTipsTimer == null)
        {
            showTipsTimer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    showTips = true;
                }
            }, 1000L * ConfigManager.showTips);
        }else
        {
            showTipsTimer.cancel();
            showTipsTimer = null;
            resetShowTipsTimer();
        }
    }

    // 向频道中的玩家们广播频道信息
    public void broadcast(int channelID,String message)
    {
        if(!channelUserMap.containsKey(channelID))return;

        switch (ConfigManager.filterMode)
        {
            case 1:
                for(String filterWords : ConfigManager.filterList)
                {
                    if(message.contains(filterWords))return;
                }
                break;
            case 2:
                for(String filterWords : ConfigManager.filterList)
                {
                    message = message.replaceAll(filterWords, "*".repeat(filterWords.length()));
                }
                break;
        }

        for(UUID uuid : channelUserMap.get(channelID))
        {
            IPlayer iPlayer = PlayerManager.getInstance().getPlayer(uuid);
            if(iPlayer.isOnline())
            {
                iPlayer.sendMessage(message);
            }
        }
        if(showTips)
        {
            for(UUID uuid : leftUserList)
            {
                IPlayer iPlayer = PlayerManager.getInstance().getPlayer(uuid);
                if(iPlayer.isOnline())
                {
                    iPlayer.sendMessage(" ");
                    iPlayer.sendMessage(ConfigManager.msgPrefix+"冷清许久的跨服频道刚刚收到了一条来自其他服务器玩家发来的消息！");
                    iPlayer.sendMessage("§7输入 §a/vgc channel §7进入跨服频道喔~ 输入 §a/vgc leave §7即可返回本服务器聊天频道。");
                    iPlayer.sendMessage(" ");
                }
            }
            showTips = false;
        }
        resetShowTipsTimer();
    }
}
