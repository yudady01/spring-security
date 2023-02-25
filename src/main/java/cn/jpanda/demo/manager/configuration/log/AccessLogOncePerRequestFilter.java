package cn.jpanda.demo.manager.configuration.log;

import cn.jpanda.demo.manager.configuration.log.entitys.AccessLogProperties;
import cn.jpanda.demo.manager.configuration.log.entitys.LogHolder;
import cn.jpanda.demo.manager.configuration.log.entitys.LogInfo;
import cn.jpanda.demo.manager.configuration.log.persistence.LogPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 记录日志
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 11:05:24
 */
@Slf4j
public class AccessLogOncePerRequestFilter extends OncePerRequestFilter {

    private LogPersistence logPersistence;

    public AccessLogOncePerRequestFilter(LogPersistence logPersistence) {
        this.logPersistence = logPersistence;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            doFilter(httpServletRequest, httpServletResponse, filterChain);
        } finally {
            LogHolder.clear();
        }
    }

    private void doFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 记录请求时间
        long start = System.currentTimeMillis();
        Date now = new Date();
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            // 记录响应时间和响应状态1
            long end = System.currentTimeMillis();
            if (LogHolder.hasLog()) {
                AccessLogProperties config = LogHolder.config();
                LogInfo log = LogHolder.log();
                if(config.isRequestTime()){
                    log.setRequestTime(now);
                }
                if (config.isDuration()) {
                    log.setDuration(end - start);
                }
                if (config.isStatus()) {
                    log.setResponseStatus(HttpStatus.valueOf(httpServletResponse.getStatus()));
                }
                doLog(log);
            }

        }

    }

    protected void doLog(LogInfo logInfo) {
        logPersistence.persistence(logInfo);
    }

}
