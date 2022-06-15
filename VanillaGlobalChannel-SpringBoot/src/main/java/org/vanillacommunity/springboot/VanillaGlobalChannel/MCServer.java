package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Data;

@Data
public class MCServer {

    private int id;
    private String displayname;
    private String account;
    private String password;

    /**
     * 查询当前服务器对象是否连接在连接池中
     * @return 当前服务器连接状态
     */
    public boolean isOnline()
    {
        return Application.wsManager.getSessionMap().containsKey(id);
    }

    @Override
    public String toString()
    {
        return id+"/"+displayname;
    }
}
