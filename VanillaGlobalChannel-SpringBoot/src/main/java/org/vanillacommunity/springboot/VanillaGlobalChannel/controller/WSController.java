package org.vanillacommunity.springboot.VanillaGlobalChannel.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import org.vanillacommunity.springboot.VanillaGlobalChannel.Application;
import org.vanillacommunity.springboot.VanillaGlobalChannel.mcserver.MCServer;


import java.io.IOException;

@ServerEndpoint("/ws/{serverID}/{serverAccount}/{serverPassword}")
@Component("wsController")
public class WSController {
    //建立连接
    @OnOpen
    public void onOpen(@PathParam("serverID") int serverID, @PathParam("serverAccount") String account, @PathParam("serverPassword") String password, Session session)
    {
        if(Application.mcServerManager.getServerMap().containsKey(serverID))
        {
            MCServer mcServer = Application.mcServerManager.getServerMap().get(serverID);
            //校验账号密码
            if(mcServer.getAccount().equals(account) && mcServer.getPassword().equals(password))
            {
                System.out.println(" ");
                System.out.println("[ Login ] "+Application.mcServerManager.getServerMap().get(serverID).getDisplayname()+" has connected");
                System.out.println(" ");
                Application.wsManager.add(serverID,session);
            }
            else
            {
                System.out.println("[ Stop ] "+mcServer.getDisplayname()+"account/password doesnt match");
                try
                {
                    session.close();
                } catch (IOException e)
                {
                    System.out.println("[ Error ] "+mcServer.getDisplayname()+"error occurred when connection closed！");
                }

            }
        }
        else
        {
            System.out.println("[ Stop ] prevent an unknown connection established");
            try
            {
                session.close();
            } catch (IOException e)
            {
                System.out.println("[ Error ] 在尝试断开连接时发生故障！");
            }
        }

    }
    @OnError
    public void onError(Session session,Throwable error)
    {
        System.out.println("error occurred while connection opened");
        error.printStackTrace();
    }

    //连接关闭
    @OnClose
    public void onClose(@PathParam("serverID") int serverID)
    {
        if(Application.mcServerManager.getServerMap().containsKey(serverID))
        {
            System.out.println(" ");
            System.out.println("[ Logout ] "+Application.mcServerManager.getServerMap().get(serverID).getDisplayname()+" has closed the connection");
            System.out.println(" ");
        }else System.out.println("[ Login ] an known mcserver has closed the connection");
        Application.wsManager.removeAndClose(serverID);
    }
    //客户端向服务端发送消息
    @OnMessage
    public void onMessage(@PathParam("serverID") int serverID, String text)
    {
        System.out.print(text);

        if(text.equalsIgnoreCase("GetChannelInfo"))
        {
            if(Application.mcServerManager.getServerMap().containsKey(serverID))
            {
                System.out.println(" success");
                sendChannelInfo(serverID);
            }
            else
            {
                System.out.println(" failed");
            }
            return;
        }
        if(text.equalsIgnoreCase("GetServerInfo"))
        {
            if(Application.mcServerManager.getServerMap().containsKey(serverID))
            {
                System.out.println(" success");
                sendServerInfo(serverID);
            }
            else
            {
                System.out.println(" failed");
            }
            return;
        }

        Application.wsManager.broadcastMessage(text);
    }
    private void sendChannelInfo(int serverID)
    {
        Session session = Application.wsManager.getSessionMap().get(serverID);
        if(session == null)return;
        Application.wsManager.sendMessage(session,"[Channel]"+Application.channelManager.getChannelInfoStr());
    }
    private void sendServerInfo(int serverID)
    {
        Session session = Application.wsManager.getSessionMap().get(serverID);
        if(session == null)return;
        Application.wsManager.sendMessage(session,"[MCServer]"+Application.mcServerManager.getServerInfoStr());
    }
}
