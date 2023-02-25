package cn.jpanda.demo.manager.configuration.security;

import cn.jpanda.demo.manager.configuration.exception.annotations.Prefix;
import cn.jpanda.demo.manager.configuration.exception.ecodes.ECode;

@Prefix("AUTH")
public enum AuthenticationECode implements ECode<String> {

    NEED_LOGIN("001", "请登录")
    ,NO_AUTHENTICATION("002", "数据访问权限不足")
    ;
    private String code;
    private String message;

    AuthenticationECode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
