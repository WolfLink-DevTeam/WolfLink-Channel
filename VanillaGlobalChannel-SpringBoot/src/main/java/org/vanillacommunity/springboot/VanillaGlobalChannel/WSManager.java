package org.vanillacommunity.springboot.VanillaGlobalChannel;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@Component("wsManager")
public class WSManager {

    @Getter
    // 服务器ID → 当前链接
    private final HashMap<Integer, Session> sessionMap = new HashMap<>();
    public void add(int serverID,Session session)
    {
        sessionMap.put(serverID,session);
    }
    public Session remove(int serverID)
    {
        return sessionMap.remove(serverID);
    }
    public void removeAndClose(int serverID)
    {
        Session session = remove(serverID);
        if(session != null)
        {
            try
            {
                session.close();
            } catch (IOException e){ e.printStackTrace(); }
        }
    }
    public Session get(int serverID)
    {
        return sessionMap.get(serverID);
    }

    public boolean sendMessage(int serverID,String message)
    {
        Session session = get(serverID);
        if(session == null)return false;
        return sendMessage(session,message);
    }

    public boolean sendMessage(Session session,String message)
    {
        if(session == null)
        {
            System.out.println("[DEBUG] 尝试给一个关闭的连接发送消息！");
            return false;
        }
        try
        {
            session.getBasicRemote().sendText(message);
            return true;
        } catch (IOException e)
        {
            System.out.println("[DEBUG] 消息发送失败");
            e.printStackTrace();
            return false;
        }
    }
    public void broadcastMessage(String message)
    {
        for(Session session : sessionMap.values())
        {
            if(session == null || !session.isOpen())continue;
            sendMessage(session,message);
        }
    }
}
