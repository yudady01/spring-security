package cn.jpanda.demo.manager.configuration.security;

import cn.jpanda.demo.manager.business.SysUserBusiness;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;


public class JPandaUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserBusiness userInfoBusiness;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userInfoBusiness.loadUserByName(s);
    }
}
