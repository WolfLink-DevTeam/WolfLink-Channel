package org.vanillacommunity.vanillaglobalchannel.common.channel;

import lombok.Getter;
import org.vanillacommunity.vanillaglobalchannel.common.*;
import org.vanillacommunity.vanillaglobalchannel.common.network.DataPack;
import org.vanillacommunity.vanillaglobalchannel.common.network.NetTransManager;
import org.vanillacommunity.vanillaglobalchannel.common.player.IPlayer;
import org.vanillacommunity.vanillaglobalchannel.common.player.PlayerManager;

import java.util.*;

public class ChannelManager {
    private static ChannelManager instance;

    private final Map<Integer,List<UUID>> channelUserMap = new HashMap<>();

    @Getter
    private final List<UUID> leftUserList = new ArrayList<>();

    public Timer timer;
    public Timer showTipsTimer;

    private final Map<Integer,Channel> channelMap = new HashMap<>();
    private final Queue<DataPack> dataPackQueue = new LinkedList<>();

    boolean showTips = false;

    public Map<Integer, Channel> getChannelMap() {
        return channelMap;
    }

    public static ChannelManager getInstance()
    {
        if(instance == null)instance = new ChannelManager();
        return instance;
    }

    public void offerPack(DataPack dataPack)
    {
        dataPackQueue.offer(dataPack);
        if(dataPackQueue.size() > 10) dataPackQueue.poll();
    }

    public void initChannel(){
        System.out.println(ConfigManager.pluginStart);
        if(NetTransManager.getInstance().getWebSocket() != null)
        {
            lookupChannelsInfo();
        }
        else
        {
            System.out.println(ConfigManager.cmdPrefix+"中央服务器连接失败！插件将不能正常运行。");
        }
    }

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
        NetTransManager.getInstance().getWebSocket().send("获取所有频道信息");
    }
    public void bindChannelsInfo(String str)
    {
        List<String> channelsInfo = Tools.stringToList(str);

        for(String channelStr : channelsInfo)
        {
            String[] args = channelStr.split("/");

            Channel channel = new Channel(Integer.parseInt(args[0]),args[1],args[2]);

            ChannelManager.getInstance().getChannelMap().put(channel.getId(),channel);
        }
        System.out.println(ConfigManager.pluginStart1);
        initChannelUserList();
        System.out.println(ConfigManager.pluginStart2);
        initChannelQueue();
        System.out.println(ConfigManager.pluginStart3);
        System.out.println(ConfigManager.pluginStartFinish);
    }

    private void initChannelUserList()
    {
        for(int channelID : channelMap.keySet())
        {
            channelUserMap.put(channelID,new ArrayList<>());
        }
    }
    private void initChannelQueue()
    {
        if(timer == null)
        {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
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
