package cn.jpanda.demo.manager.configuration.page;

import java.util.Optional;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/5 14:07:28
 */
public class PageParamHolder {

    private final static ThreadLocal<PageParam> PAGE_PARAM_THREAD_LOCAL = ThreadLocal.withInitial(() -> PageParam.builder().build());

    public PageParam getOrDefault(Integer start, Integer size) {
        return Optional.ofNullable(PAGE_PARAM_THREAD_LOCAL.get()).orElse(
                PageParam.builder()
                        .type(EPageType.GENERAL)
                        .start(start)
                        .size(size)
                        .build()
        );
    }

    public PageParam getOrDefault(String startId, Integer size) {
        return Optional.ofNullable(PAGE_PARAM_THREAD_LOCAL.get()).orElse(
                PageParam.builder()
                        .type(EPageType.SCROLL)
                        .startId(startId)
                        .size(size)
                        .build()
        );
    }
}
