package cn.jpanda.demo.manager.configuration.log.persistence;


import cn.jpanda.demo.manager.configuration.log.entitys.LogInfo;

/**
 * 日志持久化
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 13:18:54
 */
public interface LogPersistence {

    void persistence(LogInfo log);
}
