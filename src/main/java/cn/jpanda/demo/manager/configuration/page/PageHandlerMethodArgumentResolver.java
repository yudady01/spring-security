package cn.jpanda.demo.manager.configuration.page;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;
import java.util.Optional;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/5 13:59:36
 */
@Slf4j
@Builder
public class PageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    // 默认值

    private static final String DEFAULT_TYPE_PARAMETER = "type";
    private static final String DEFAULT_PAGE_PARAMETER = "page";
    private static final String DEFAULT_SIZE_PARAMETER = "size";
    private static final String DEFAULT_ID_PARAMETER = "lastId";

    /**
     * 类型参数名称
     */
    private String typeParameterName = DEFAULT_TYPE_PARAMETER;
    /**
     * 当前页参数名称
     */
    private String pageParameterName = DEFAULT_PAGE_PARAMETER;
    /**
     * 每页展示数量参数名称
     */
    private String sizeParameterName = DEFAULT_SIZE_PARAMETER;
    /**
     * id参数名称
     */
    private String idParameterName = DEFAULT_ID_PARAMETER;

    /**
     * 默认每页最大展示数量
     */
    private Integer maxPageSize = 2000;

    /**
     * 默认分页大小
     */
    private Integer defaultSize = 10;

    /**
     * 默认当前页
     */
    private Integer defaultPage = 1;

    /**
     * 开发模式才会触发编码不规范导致的异常问题
     */
    private boolean idDev = false;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 限制注解和类型

        // step1: 校验参数类型
        if (PageParam.class.equals(methodParameter.getParameterType())) {

            if (methodParameter.hasParameterAnnotation(Page.class)) {
                return true;
            }
            // 如果开发者未指定Page注解,记录warn日志
            if (log.isWarnEnabled()) {

                log.warn("参数[{}]类型为:{},但因未配置{}注解,将无法完成设值操作."
                        , methodParameter.getParameterName()
                        , methodParameter.getParameterType()
                        , Page.class
                );
            }
        }


        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter
            , ModelAndViewContainer modelAndViewContainer
            , NativeWebRequest nativeWebRequest
            , WebDataBinderFactory webDataBinderFactory
    ) throws Exception {
        // 加载Page注解
        Page pageAnnotation = methodParameter.getParameterAnnotation(Page.class);

        // 加载数据
        String typeName = Optional.ofNullable(nativeWebRequest.getParameter(typeParameterName)).map(s -> s.toUpperCase().trim()).orElse(EPageType.AUTO.name());

        EPageType currentType = EPageType.valueOf(typeName);
        // 类型校验
        if (typeNotSupported(Objects.requireNonNull(pageAnnotation).supportPagingType(), currentType)) {
            return null;
        }

        // 加载参数
        String id = nativeWebRequest.getParameter(idParameterName);
        Integer page = strToInteger(nativeWebRequest.getParameter(pageParameterName));
        Integer size = strToInteger(nativeWebRequest.getParameter(sizeParameterName));

        // 根据类型校验参数


        return loadPageParam(pageAnnotation, currentType, id, page, size);
    }

    private Object loadPageParam(Page pageAnnotation, EPageType type, String id, Integer page, Integer size) {
        switch (type) {
            case GENERAL: {
                return loadGeneralPageParam(pageAnnotation, id, page, size);
            }
            case SCROLL: {
                return loadScrollPageParam(pageAnnotation, id, page, size);
            }
            case AUTO: {
                return loadAutoPageParam(pageAnnotation, id, page, size);
            }
            default: {
                return null;
            }
        }
    }

    private PageParam loadAutoPageParam(Page pageAnnotation, String id, Integer page, Integer size) {
        // 选择模式
        return page == null
                ? loadScrollPageParam(pageAnnotation, id, page, size)
                : loadGeneralPageParam(pageAnnotation, id, page, size);


    }

    private PageParam loadGeneralPageParam(Page pageAnnotation, String id, Integer page, Integer size) {
        // 校验id参数
        if (idDev) {
            if (StringUtils.hasText(id)) {
                // 同时传入id和page.编码不规范,研发环境将触发异常
            }
        }

        if (pageAnnotation.allowDefaultPagingValue()) {
            // 应用默认值
            if (page == null) {
                page = defaultPage;
            }
            if (size == null) {
                size = defaultSize;
            }
        }

        // 校验参数
        if (page == null || size == null) {

        }
        return PageParam.of(page, size);
    }

    private PageParam loadScrollPageParam(Page pageAnnotation, String id, Integer page, Integer size) {
        // 校验id参数
        if (idDev) {
            if (page != null) {
                // 同时传入id和page.编码不规范,研发环境将触发异常
            }
        }

        if (pageAnnotation.allowDefaultPagingValue()) {
            if (size == null) {
                size = defaultSize;
            }
        }

        if (StringUtils.isEmpty(id)
                && (!pageAnnotation.allowScrollingPagingIDToBeNull())
        ) {
            // TODO  用户限制滚动分页模式下,分页ID为必传

        }
        // 校验参数,但不校验ID
        if (size == null) {

        }
        return PageParam.of(id, size);
    }

    private boolean typeNotSupported(EPageType supports, EPageType current) {
        if (supports == null) {
            return false;
        }
        if (supports.equals(EPageType.AUTO)) {
            return true;
        }
        return supports.equals(current);
    }

    public Integer strToInteger(String s) {
        if (s == null) {
            return null;
        }
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            // TODO 转换为自定义异常
            throw new RuntimeException("");
        }

    }
}
