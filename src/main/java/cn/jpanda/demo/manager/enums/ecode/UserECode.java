package cn.jpanda.demo.manager.enums.ecode;

import cn.jpanda.demo.manager.configuration.exception.annotations.Prefix;
import cn.jpanda.demo.manager.configuration.exception.ecodes.ECode;

/**
 * 用户相关异常码
 */
@Prefix("USER")
public enum UserECode implements ECode<String> {
    USER_IS_NULL("001", "无法获取指定用户数据"),
    USER_NAME_IS_NULL("002", "用户名不得为空"),
    USER_LOGIN_NAME_IS_NULL("003", "用户登录名不得为空"),
    USER_PASSWORD_IS_NULL("004", "用户登录密码不得为空"),
    USER_ID_IS_NULL("005", "用户ID不得为空"),
    USER_NAME_OR_PASSWORD_ERROR("006", "用户名或密码错误"),
    USER_NOT_EXIST("007", "用户不存在");
    private String code;
    private String message;

    UserECode(String code, String message) {
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
