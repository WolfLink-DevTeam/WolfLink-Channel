package org.wolflink.minecraft;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface HttpAPI {
    Client queryClient(String client_account);
    Channel queryChannel(int channel_id);
    Set<Channel> queryAllChannels();
    Set<Client> queryChannelOnlineClients(int channel_id);
    Set<Client> queryAllOnlineClients();

}
