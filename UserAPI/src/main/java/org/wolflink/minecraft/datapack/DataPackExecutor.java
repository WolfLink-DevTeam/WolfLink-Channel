package org.wolflink.minecraft.datapack;

import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.*;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.file.PermanentData;
import org.wolflink.minecraft.interfaces.IPlayer;
import org.wolflink.minecraft.interfaces.PlatformAdapter;
import org.wolflink.minecraft.network.ServerCachePool;

import java.io.StringReader;

@Singleton
public class DataPackExecutor {
    @Inject
    private PlatformAdapter platformAdapter;
    @Inject
    PermanentData permanentData;
    @Inject
    Language language;
    @Inject
    ServerCachePool serverCachePool;
    public void execute(DataPack dataPack) {
        if(dataPack.getType().equals(MsgType.CHANNEL)) {
            GlobalMessage globalMessage = GlobalMessage.fromJson(dataPack.getContent().getAsJsonObject());
            Client client = serverCachePool.getClient(globalMessage.getClientAccount());
            String serverName;
            if(client != null) serverName = client.getDisplayName();
            else serverName = "§7SID"+globalMessage.getClientAccount();
            String chatMsg = language.getChatTemplate(serverName,globalMessage.getSenderDisplayName(),globalMessage.getContent());
            platformAdapter.getOnlinePlayers()
                    .stream().filter(iPlayer ->
                            permanentData.getChannelPlayers().contains(iPlayer.getName())
                                    && iPlayer.isOnline())
                    .forEach(iPlayer -> iPlayer.sendMessage(chatMsg));
        }
    }
}
