package org.vanillacommunity.vanillaglobalchannel.velocity;

import org.vanillacommunity.vanillaglobalchannel.common.PlatformAdapter;

import java.nio.file.Path;

/**
 * Velocity用的适配器，在Common模块中调用
 */
public class VelocityAdapter extends PlatformAdapter{

    @Override
    public Path getPath(){
        return Velocity.getInstance().getPath();
    }
}
