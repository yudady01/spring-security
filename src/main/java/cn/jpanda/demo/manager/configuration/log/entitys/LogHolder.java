package cn.jpanda.demo.manager.configuration.log.entitys;

import java.util.Optional;

/**
 * 日志记录器
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 11:31:06
 */
public class LogHolder {
    private static AccessLogProperties DEFAULT_ACCESS_LOG_PROPERTIES = new AccessLogProperties();

    /**
     * 当前记录的日志数据
     */
    private static final ThreadLocal<LogInfo> LOG_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<AccessLogProperties> CONFIG_THREAD_LOCAL = ThreadLocal.withInitial(LogHolder::createDefaultConfig);

    public static boolean hasLog() {
        return Optional.ofNullable(LOG_THREAD_LOCAL.get()).isPresent();
    }

    public static LogInfo log() {
        return LOG_THREAD_LOCAL.get();
    }

    public static void log(LogInfo log) {
        LOG_THREAD_LOCAL.set(log);
    }

    public static void clear() {
        LOG_THREAD_LOCAL.remove();
        CONFIG_THREAD_LOCAL.remove();
    }

    public static AccessLogProperties config() {
        return CONFIG_THREAD_LOCAL.get();
    }

    public static void config(AccessLogProperties accessLogProperties) {
        CONFIG_THREAD_LOCAL.set(accessLogProperties);
    }

    public static AccessLogProperties createDefaultConfig() {
        return DEFAULT_ACCESS_LOG_PROPERTIES.clone();
    }

    public static void initDefaultConfig(AccessLogProperties accessLogProperties) {
        DEFAULT_ACCESS_LOG_PROPERTIES = accessLogProperties;
    }
}
