package org.vanillacommunity.springboot.VanillaGlobalChannel.controller;

import org.springframework.stereotype.Component;
import org.vanillacommunity.springboot.VanillaGlobalChannel.Application;
import org.vanillacommunity.springboot.VanillaGlobalChannel.MCServer;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/ws/{serverID}/{serverAccount}/{serverPassword}")
@Component("wsController")
public class WSController {
    //建立连接
    @OnOpen
    public void onOpen(@PathParam("serverID") int serverID,@PathParam("serverAccount") String account,@PathParam("serverPassword") String password, Session session)
    {
        if(Application.mcServerManager.getServerMap().containsKey(serverID))
        {
            MCServer mcServer = Application.mcServerManager.getServerMap().get(serverID);
            //校验账号密码
            if(mcServer.getAccount().equals(account) && mcServer.getPassword().equals(password))
            {
                System.out.println(" ");
                System.out.println("[ 建立连接 ] "+Application.mcServerManager.getServerMap().get(serverID).getDisplayname()+" 刚刚连接到了世界频道！");
                System.out.println(" ");
                Application.wsManager.add(serverID,session);
            }
            else
            {
                System.out.println("[ 阻止连接 ] "+mcServer.getDisplayname()+"尝试建立连接，但是账号密码不一致！");
                try
                {
                    session.close();
                } catch (IOException e)
                {
                    System.out.println("[ 故障 ] "+mcServer.getDisplayname()+"在尝试断开连接时发生故障！");
                }

            }
        }
        else
        {
            System.out.println("[ 阻止连接 ] 刚刚阻止了一个不明连接。");
            try
            {
                session.close();
            } catch (IOException e)
            {
                System.out.println("[ 故障 ] 在尝试断开连接时发生故障！");
            }
        }

    }
    @OnError
    public void onError(Session session,Throwable error)
    {
        System.out.println("建立连接时出现一个错误");
        error.printStackTrace();
    }

    //连接关闭
    @OnClose
    public void onClose(@PathParam("serverID") int serverID)
    {
        if(Application.mcServerManager.getServerMap().containsKey(serverID))
        {
            System.out.println(" ");
            System.out.println("[ 断开连接 ] "+Application.mcServerManager.getServerMap().get(serverID).getDisplayname()+" 刚刚断开了与世界频道的连接。");
            System.out.println(" ");
        }else System.out.println("[ 建立连接 ] 刚刚一个不明服务器中止了与世界频道的连接。");
        Application.wsManager.removeAndClose(serverID);
    }
    //客户端向服务端发送消息
    @OnMessage
    public void onMessage(@PathParam("serverID") int serverID, String text)
    {
        System.out.println(text);

        if(text.equalsIgnoreCase("获取所有频道信息"))
        {
            if(Application.mcServerManager.getServerMap().containsKey(serverID)) sendChannelInfo(serverID);
            return;
        }
        if(text.equalsIgnoreCase("获取所有服务器信息"))
        {
            if(Application.mcServerManager.getServerMap().containsKey(serverID))sendServerInfo(serverID);
            return;
        }

        Application.wsManager.broadcastMessage(text);
    }
    private void sendChannelInfo(int serverID)
    {
        Session session = Application.wsManager.getSessionMap().get(serverID);
        if(session == null)return;
        Application.wsManager.sendMessage(session,"[频道信息]"+Application.channelManager.getChannelInfoStr());
    }
    private void sendServerInfo(int serverID)
    {
        Session session = Application.wsManager.getSessionMap().get(serverID);
        if(session == null)return;
        Application.wsManager.sendMessage(session,"[服务器信息]"+Application.mcServerManager.getServerInfoStr());
    }
}
