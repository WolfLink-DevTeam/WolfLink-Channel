package org.vanillacommunity.solon;

import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Singleton;
import org.noear.solon.core.util.LogUtil;

/**
 * 日志类的规范封装
 */
@Singleton(true)
@Component
public class Logger {
    private static final String PREFIX = "VGC-Server";

    public void err(String text) {
        String result = String.format("[%s|%5s] %s", PREFIX, "ERROR", text);
        LogUtil.global().error(result);
    }

    public void warn(String text) {
        String result = String.format("[%s|%5s] %s", PREFIX, "WARN", text);
        LogUtil.global().warn(result);
    }

    public void info(String text) {
        String result = String.format("[%s|%5s] %s", PREFIX, "INFO", text);
        LogUtil.global().info(result);
    }
}
