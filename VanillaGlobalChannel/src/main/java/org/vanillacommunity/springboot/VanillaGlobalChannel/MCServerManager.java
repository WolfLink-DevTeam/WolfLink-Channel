package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Configuration("mcServerManager")
@Order(1)
public class MCServerManager extends Manager{

    public static MCServerManager instance;

    @Setter
    @Getter
    Map<Integer,MCServer> serverList;

    public MCServerManager()
    {
        serverList = new HashMap<>();
        MCServerManager.instance = this;
    }
    @Override
    public void init()
    {
        // TODO 记得写好配置文件后来修改这边
        serverList.put(1,new MCServer(1,"§6狼与香辛料亭"));
        serverList.put(2,new MCServer(2,"§d璎珞"));
    }
}
