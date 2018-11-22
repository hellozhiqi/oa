package org.fkjava.security.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.fkjava.identity.domain.User;
import org.fkjava.identity.service.IdentityService;
import org.fkjava.security.domain.UserDetails;
import org.fkjava.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private IdentityService identityService;
	
	/**
	 * 校验登录名是否存在，以及为每个roleKey添加 ROLE_
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> optional=identityService.findByLoginName(username);
		User user=optional.orElseThrow(()->{
			return new UsernameNotFoundException(username+"没有匹配到!");
		}) ;
		Collection<GrantedAuthority> authorities =new HashSet<>();
		// 获取所有的角色，在角色的KEY前面加上ROLE_开头作为【已授权的身份】
		user.getRoles().forEach(role->{
			 GrantedAuthority ga = new SimpleGrantedAuthority("ROLE_"+role.getRoleKey());
			authorities.add(ga);
		});
		UserDetails details=new  UserDetails(user, authorities);
		
		return details;
	}
}
