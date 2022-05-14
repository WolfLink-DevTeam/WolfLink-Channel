package org.vanillacommunity.springboot.VanillaGlobalChannel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class DataPackController {

    //发送数据包 返回是否成功。
    @ResponseBody
    @RequestMapping("/chat/{channelID}/{serverDisplayName}/{playerDisplayName}/{message}")
    public Boolean getDataPack(@PathVariable int channelID,@PathVariable String serverDisplayName,
                          @PathVariable String playerDisplayName ,@PathVariable String message) {

        DataPack dataPack = new DataPack(channelID,serverDisplayName,playerDisplayName,message);

        return ChannelManager.getInstance().sendDataPack(dataPack);
    }

    @ResponseBody
    @RequestMapping("/testInfo/{channelID}")
    public String getChannelQueueInfo(@PathVariable int channelID)
    {
        Channel channel = ChannelManager.getInstance().channelMap.get(channelID);
        List<String> resultList = new ArrayList<>();
        channel.queueMap.values().forEach(queue -> queue.forEach(dataPack -> resultList.add(
                dataPack.getChannelID()+"|"+dataPack.getServerDisplayName()+"|"+dataPack.getPlayerDisplayName()+"|"+dataPack.getMessage())));
        return resultList.toString();
    }
    @ResponseBody
    @RequestMapping("/testParseDataPack/{dataPackStr}")
    public String parseDataPack(@PathVariable String dataPackStr)
    {
        DataPack dataPack = DataPack.fromString(dataPackStr);
        return dataPack.toString();
    }
    /**
     * 消费队列内的消息，将数据包以String格式返回
     * @param channelID 频道ID
     * @param serverID 服务器ID
     * @return 消息内容
     */
    @ResponseBody
    @RequestMapping("/consume/{channelID}/{serverID}")
    public String consumeDataPack(@PathVariable int channelID,@PathVariable int serverID)
    {
        Channel channel = ChannelManager.getInstance().channelMap.get(channelID);
        DataPack dataPack = channel.queueMap.get(serverID).poll();
        if(dataPack == null)return "null";
        return dataPack.toString();
    }
}
