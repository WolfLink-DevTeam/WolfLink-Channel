package org.wolflink.minecraft.network;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.wolflink.common.ioc.Inject;
import org.wolflink.common.ioc.Singleton;
import org.wolflink.minecraft.DataPack;
import org.wolflink.minecraft.datapack.DataPackExecutor;
import org.wolflink.minecraft.datapack.DataPackParser;
import org.wolflink.minecraft.file.Configuration;
import org.wolflink.minecraft.file.Language;
import org.wolflink.minecraft.interfaces.ILogger;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class WSListener extends WebSocketListener {

    @Inject
    private ILogger logger;
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

    // 在 WebSocket 连接建立时调用该方法进行处理
    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        if(response.isSuccessful()) {
            logger.info(language.getPrefix()+"中央聊天服务器连接成功。");
        } else {
            logger.warn(language.getPrefix()+"与中央聊天服务器建立连接时出现问题：");
            logger.warn(response.message());
            try {
                if(response.body() != null) {
                    logger.warn(response.body().string());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // 在收到 WebSocket 连接中对方发来的消息时调用该方法进行处理
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        DataPack dataPack = parser.parse(text);
        if(dataPack == null) return;
        executor.execute(dataPack);
    }
    // 在 WebSocket 连接关闭时调用该方法进行处理
    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        if(code != 1012) {
            logger.info(language.getPrefix()+"与中央服务器的连接已断开，插件停止工作。");
        }
    }
    // 在 WebSocket 连接失败时调用该方法进行处理
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable throwable, Response response) {
    }

}
