package org.wolflink.minecraft.network;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.Application;
import org.wolflink.minecraft.datapack.DataPackExecutor;
import org.wolflink.minecraft.datapack.DataPackParser;
import org.wolflink.minecraft.file.Configuration;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.interfaces.ILogger;

import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class WSListener extends WebSocketListener {

    private final ILogger logger = Application.getLogger();
    @Inject
    private Network network;
    @Inject
    private Configuration configuration;
    @Inject
    private Language language;
    @Inject
    private DataPackExecutor executor;
    @Inject
    private DataPackParser parser;

    private static int lastWaitingReconnect = 1;
    private static int nowWaitingReconnect = 1;//秒

    // 在 WebSocket 连接建立时调用该方法进行处理
    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        logger.info(language.getPrefix()+"中央聊天服务器连接成功。");
        lastWaitingReconnect = 1;
        nowWaitingReconnect = 1;
    }
    // 在收到 WebSocket 连接中对方发来的消息时调用该方法进行处理
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        executor.execute(parser.parse(text));
    }
    // 在 WebSocket 连接关闭时调用该方法进行处理
    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        logger.info(language.getPrefix()+"与中央服务器的连接已断开，插件停止工作。");
    }
    // 在 WebSocket 连接失败时调用该方法进行处理
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable throwable, Response response) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.warn(language.getPrefix()+"中央聊天服务器连接失败，正在重试...");
                network.setEnabled(false);
                network.setEnabled(true);
                int temp = nowWaitingReconnect;
                nowWaitingReconnect+=lastWaitingReconnect;
                lastWaitingReconnect=temp;
                if(nowWaitingReconnect > 3600)nowWaitingReconnect = 3600;
            }
        },nowWaitingReconnect * 1000L);

    }

}
