package cn.jpanda.demo.manager.business;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户操作业务模块
 */
public interface SysUserBusiness {
    /**
     * 根据用户名加载用户数据
     *
     * @param name 用户名
     * @return 用户数据
     */
    UserDetails loadUserByName(String name);
}
