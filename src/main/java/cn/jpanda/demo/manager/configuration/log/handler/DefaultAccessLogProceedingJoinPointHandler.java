package cn.jpanda.demo.manager.configuration.log.handler;

import cn.jpanda.demo.manager.configuration.log.annotations.AccessLog;
import cn.jpanda.demo.manager.configuration.log.annotations.LogOptions;
import cn.jpanda.demo.manager.configuration.log.entitys.*;
import cn.jpanda.demo.manager.configuration.log.handler.filters.Filter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 16:35:40
 */
@Component
public class DefaultAccessLogProceedingJoinPointHandler implements AccessLogProceedingJoinPointHandler {
    @Override
    public Object handler(MethodInvocation point) throws Throwable {

        Object result = null;
        Throwable throwable = null;
        try {
            // 处理请求
            result = point.proceed();
        } catch (Exception e) {
            throwable = e;
        }


        // 处理日志参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();


        Method method = point.getMethod();

        LogInfo logInfo = new LogInfo();
        logInfo.setDescription(readMethodDescription(method));

        // 获取配置对象
        AccessLogProperties config = loadCustomConfig(AnnotationUtils.findAnnotation(method, LogOptions.class));

        if (config.isUri()) {
            logInfo.setUri(request.getRequestURL().toString());
        }
        if (config.isHttpMethod()) {
            logInfo.setHttpMethod(HttpMethod.resolve(request.getMethod()));
        }
        if (config.isHeaders()) {
            // 加载获取
            Class<? extends Filter> headerFilter = config.getHeadersFilter();
            Filter filter = headerFilter.newInstance();
            Enumeration<String> enumeration = request.getHeaderNames();
            Map<String, String> matchHeaders = new HashMap<>();
            while (enumeration.hasMoreElements()) {
                String headerName = enumeration.nextElement();
                String headerValue = request.getHeader(headerName);
                if (filter.filter(headerName)) {
                    matchHeaders.put(headerName, headerValue);
                }
            }
            logInfo.setHeaders(matchHeaders);
        }

        if (config.isSignature()) {
            Signature signature1 = new Signature();
            signature1.setClassName(point.getThis().getClass().getCanonicalName());
            signature1.setMethodName(point.getMethod().getName());
            logInfo.setSignature(signature1);
        }

        if (config.isParams()) {
            logInfo.setArgs(castParams(method, point.getArguments()));
        }
        if (config.isIp()) {
            logInfo.setRequestIp(loadRealIp(request));
        }

        if (throwable != null) {
            if (config.isErrMessage()) {
                logInfo.setErrMessage(throwable.getMessage());
            }
            if (config.isErrTrace()) {
                logInfo.setErrTrace(throwable);
            }
        } else {
            if (config.isResult()) {
                logInfo.setResult(result);
            }

        }
        if (throwable != null) {
            throw throwable;
        }

        LogHolder.log(logInfo);
        // 返回请求结果
        return result;
    }


    public AccessLogProperties loadCustomConfig(LogOptions logOptions) {
        return Optional.ofNullable(logOptions).map(opt -> {
            AccessLogProperties properties = new AccessLogProperties();
            properties.setUri(opt.uri());
            properties.setHeaders(opt.headers());
            properties.setHeadersFilter(opt.headersFilter());
            properties.setHttpMethod(opt.httpMethod());
            properties.setSignature(opt.signature());
            properties.setParams(opt.params());
            properties.setIp(opt.ip());
            properties.setDuration(opt.duration());
            properties.setStatus(opt.status());
            properties.setResult(opt.result());
            properties.setErrMessage(opt.errMessage());
            properties.setErrTrace(opt.errTrace());

            LogHolder.config(properties);
            return properties;
        }).orElse(LogHolder.config());
    }

    /**
     * 读取方法描述
     *
     * @param method 方法
     * @return 方法描述或者方法名称
     */
    protected String readMethodDescription(Method method) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        // 获取方法描述注解
        String description = method.getName();
        // 读取swagger注解
        AccessLog accessLog = AnnotationUtils.findAnnotation(method, AccessLog.class);
        if (accessLog != null) {
            // 获取参数描述
            description = accessLog.value();
        }
        return description;
    }

    protected Param[] castParams(Method method, Object[] args) {
        if (args == null) {
            return null;
        }
        Class<?>[] types = method.getParameterTypes();
        String[] argNames = Arrays.stream(method.getParameters()).map(Parameter::getName).toArray(String[]::new);

        Param[] params = new Param[args.length];
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            // 读取swagger注解
            params[i] = Param.builder()
                    .argName(argNames[i])
                    .name(argNames[i])
                    .type(types[i])
                    .value(args[i])
                    .build();

        }
        return params;
    }

    protected String loadRealIp(HttpServletRequest request) {
        for (String header : Arrays.asList("x-forwarded-for"
                , "Proxy-Client-IP"
                , "WL-Proxy-Client-IP"
                , "HTTP_CLIENT_IP"
                , "HTTP_X_FORWARDED_FOR"
                , "X-Real-IP")) {
            String value = request.getHeader(header);
            if (StringUtils.hasText(value)) {
                if (value.contains(",")) {
                    String[] ips = value.split(",");
                    return ips[ips.length - 1];
                } else {
                    return value;
                }
            }
        }
        return "unknown";
    }
}
