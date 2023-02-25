package cn.jpanda.demo.manager.configuration.log.persistence;

import cn.jpanda.demo.manager.configuration.log.entitys.LogInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的日志持久化策略
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 13:20:00
 */
@Slf4j
public class DefaultLogPersistence implements LogPersistence {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void persistence(LogInfo logInfo) {
        log.info("访问日志:{}", objectMapper.writeValueAsString(logInfo));
    }

}
