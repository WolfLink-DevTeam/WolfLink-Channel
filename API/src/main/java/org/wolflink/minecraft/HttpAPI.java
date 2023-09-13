package org.wolflink.minecraft;

import java.util.Set;

public interface HttpAPI {
    Client queryClient(String client_account);
    Channel queryChannel(int channel_id);
    Set<Client> queryChannelOnlineClients(int channel_id);

}
