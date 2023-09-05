package org.wolflink.minecraft.actions;

import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Result;
import org.wolflink.minecraft.file.PermanentData;
import org.wolflink.minecraft.interfaces.IPlayer;
@Singleton
public class ChannelAction {
    @Inject
    private PermanentData permanentData;
    public Result playerJoin(IPlayer player) {
        if(permanentData.getChannelPlayers().contains(player.getName())) {
            return new Result(false,"你已经处于频道中。");
        }
        permanentData.getChannelPlayers().add(player.getName());
        return new Result(true,"加入成功。");
    }
    public Result playerLeave(IPlayer player) {
        if(!permanentData.getChannelPlayers().remove(player.getName())) {
            return new Result(false,"你没有处于频道中。");
        }
        permanentData.getChannelPlayers().remove(player.getName());
        return new Result(true,"离开成功。");
    }
}
