package org.wolflink.minecraft.actions;

import org.wolflink.common.ioc.IOC;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Result;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.file.PermanentData;
import org.wolflink.minecraft.interfaces.IPlayer;
@Singleton
public class ChannelAction {
    @Inject
    private PermanentData permanentData;
    @Inject
    private Language language;

    /**
     * 玩家加入频道动作
     */
    public Result playerJoin(IPlayer player) {
        if(permanentData.getChannelPlayers().contains(player.getName())) {
            return new Result(false,language.getAlreadyInChannel());
        }
        permanentData.getChannelPlayers().add(player.getName());
        return new Result(true, language.getChannelJoin());
    }

    /**
     * 玩家离开频道动作
     */
    public Result playerLeave(IPlayer player) {
        if(!permanentData.getChannelPlayers().remove(player.getName())) {
            return new Result(false,language.getNotInChannel());
        }
        permanentData.getChannelPlayers().remove(player.getName());
        return new Result(true,language.getChannelLeave());
    }
}
