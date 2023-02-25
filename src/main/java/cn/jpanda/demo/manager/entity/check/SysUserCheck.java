package cn.jpanda.demo.manager.entity.check;

import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeException;
import cn.jpanda.demo.manager.entity.SysUser;
import cn.jpanda.demo.manager.enums.ecode.UserECode;
import org.springframework.util.StringUtils;

/**
 * 用户数据校验
 */
public class SysUserCheck {

    private SysUser userInfo;

    private SysUserCheck(SysUser userInfo) {
        this.userInfo = userInfo;
    }

    public static SysUserCheck of(SysUser userInfo) {
        if (null == userInfo) {
            throw BusinessRuntimeException.exception(UserECode.USER_IS_NULL);
        }
        return new SysUserCheck(userInfo);
    }

    public SysUserCheck loginName() {
        if (!StringUtils.hasText(userInfo.getUsername())) {
            throw BusinessRuntimeException.exception(UserECode.USER_LOGIN_NAME_IS_NULL);
        }
        return this;
    }

    public SysUserCheck userId() {
        if (!StringUtils.hasText(userInfo.getId())) {
            throw BusinessRuntimeException.exception(UserECode.USER_IS_NULL);
        }
        return this;
    }

    public SysUserCheck loginPassword() {
        if (!StringUtils.hasText(userInfo.getPassword())) {
            throw BusinessRuntimeException.exception(UserECode.USER_PASSWORD_IS_NULL);
        }
        return this;
    }

}
