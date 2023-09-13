package org.vanillacommunity.solon.config;

import lombok.Getter;
import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Component;
import org.wolflink.common.ioc.Singleton;

@Singleton
@Getter
public class MainConfig implements ILoadable {

    int queryPerMinuteLimit = 0;

    @Override
    public void load(SolonApp solonApp) {
        queryPerMinuteLimit = solonApp.cfg().getInt("api.query_per_minute_limit",0);
    }
}
