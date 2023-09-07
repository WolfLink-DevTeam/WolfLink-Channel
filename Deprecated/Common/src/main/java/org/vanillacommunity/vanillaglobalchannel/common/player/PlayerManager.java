package org.vanillacommunity.vanillaglobalchannel.common.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 玩家管理者，本质上就是个 PlayerRepository
 */
public class PlayerManager {
    private static PlayerManager instance;

    private Map<UUID, IPlayer> playerMap = new HashMap<>();

    public static PlayerManager getInstance()
    {
        if(instance == null)instance = new PlayerManager();
        return instance;
    }

    public Map<UUID,IPlayer> getPlayerMap()
    {
        return playerMap;
    }

    public IPlayer getPlayer(UUID uuid)
    {
        return playerMap.get(uuid);
    }
}
