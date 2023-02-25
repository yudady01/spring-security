package cn.jpanda.demo.manager.business.impl;

import cn.jpanda.demo.manager.business.SysUserBusiness;
import cn.jpanda.demo.manager.configuration.exception.BusinessRuntimeException;
import cn.jpanda.demo.manager.entity.SysUser;
import cn.jpanda.demo.manager.entity.check.SysUserCheck;
import cn.jpanda.demo.manager.enums.ecode.UserECode;
import cn.jpanda.demo.manager.service.SysUserService;
import cn.jpanda.demo.manager.vo.LoginUser;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户信息操作业务模块
 */
@Slf4j
@Service
public class SysUserBusinessImpl implements SysUserBusiness {

	@Resource
	private SysUserService sysUserService;


	/**
	 * 通过用户登录名称加载用户数据
	 *
	 * @param name 用户名
	 * @return 用户数据
	 */
	@Override
	public UserDetails loadUserByName(String name) {
		SysUser user = new SysUser();
		user.setUsername(name);
		user = sysUserService.selectOne(new EntityWrapper<>(user));
		if (user == null) {
			throw BusinessRuntimeException.exception(UserECode.USER_NOT_EXIST);
		}
		return cover2UserDetails(user).orElse(null);
	}

	/**
	 * 将用户对象转换为鉴权对象
	 *
	 * @param userInfo 普通用户对象
	 * @return 鉴权用户对象
	 */
	private Optional<UserDetails> cover2UserDetails(SysUser userInfo) {

		// 数据校验
		SysUserCheck.of(userInfo).userId().loginName().loginPassword();

		UserDetails userDetails = User.builder()
				.username(userInfo.getUsername())
				.password(userInfo.getPassword())
				.authorities(generatorRoleName("ADMIN"))
				.build();
		return Optional.of(LoginUser.LoginUserBuilder.with(userDetails).build());
	}

	/**
	 * 将数据库用户角色转换为Security角色名称
	 *
	 * @param role 数据库用户角色名称
	 * @return 标准Security角色名称
	 */
	private String generatorRoleName(String role) {
		return "ROLE_" + role;
	}
}
