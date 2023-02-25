package cn.jpanda.demo.manager.configuration.page;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/5 13:59:58
 */
public @interface Page {
    /**
     * 忽略分页最大限制
     */
    boolean ignoreMaximumLimit() default false;

    /**
     * 允许使用默认的分页参数
     */
    boolean allowDefaultPagingValue() default true;

    /**
     * 允许在滚动分页模式下,传入空ID,通常该场景应用在第一页
     */
    boolean allowScrollingPagingIDToBeNull() default true;

    /**
     * 默认支持的分页方式
     */
    EPageType supportPagingType() default EPageType.GENERAL;
}
