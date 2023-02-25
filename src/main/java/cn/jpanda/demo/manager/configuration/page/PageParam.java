package cn.jpanda.demo.manager.configuration.page;

import lombok.Builder;
import lombok.Data;

/**
 * 分页请求参数
 *
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/5 13:53:21
 */
@Data
@Builder
public class PageParam {
    /**
     * 分页类型
     */
    private EPageType type;
    /**
     * 起始页
     */
    private Integer start;
    /**
     * 每页展示数量
     */
    private Integer size;

    /**
     * 滚动分页时,上次加载的最后一条记录的唯一标志
     */
    private String startId;

    public static PageParam of(Integer start, Integer size) {
        return PageParam.builder()
                .type(EPageType.GENERAL)
                .start(start)
                .size(size)
                .build();
    }

    public static PageParam of(String startId, Integer size) {
        return PageParam.builder()
                .type(EPageType.SCROLL)
                .startId(startId)
                .size(size)
                .build();
    }
}
