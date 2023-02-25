package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.util.Objects;

/**
 * 处理金额参数
 */
public class MoneyMethodArgumentResolver extends RequestParamMethodArgumentResolver {
    public MoneyMethodArgumentResolver() {
        super(true);
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Money.class)&&Number.class.isAssignableFrom(methodParameter.getParameter().getType());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Object o=super.resolveName(name, parameter, request);
        Money money=parameter.getParameterAnnotation(Money.class);
        return Objects.requireNonNull(money).strategy().getMoneyConvertStrategy().convert(o,money);

    }
}
