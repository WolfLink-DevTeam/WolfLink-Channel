package org.wolflink.minecraft.datapack;

import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Application;
import org.wolflink.minecraft.DataPack;
import org.wolflink.minecraft.GlobalMessage;
import org.wolflink.minecraft.MsgType;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.interfaces.PlatformAdapter;

@Singleton
public class DataPackExecutor {
    public void execute(DataPack dataPack) {
        if(dataPack.getType().equals(MsgType.CHANNEL)) {
            GlobalMessage globalMessage = GlobalMessage.fromJson(dataPack.getContent());
            String chatTemplate = IOC.getBean(Language.class).getChatTemplate();
            String chatMsg = chatTemplate
                    .replace("%sender%",globalMessage.getSenderDisplayName())
                    .replace("%content%", globalMessage.getContent());
            // TODO 判断玩家是否处于全球频道
            IOC.getBean(PlatformAdapter.class).getOnlinePlayers().forEach(iPlayer -> iPlayer.sendMessage(chatMsg));
        }
    }
}
