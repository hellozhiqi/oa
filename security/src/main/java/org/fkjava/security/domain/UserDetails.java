package org.fkjava.security.domain;

import java.util.Collection;

import org.fkjava.identity.domain.User;
import org.springframework.security.core.GrantedAuthority;

public class UserDetails extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	// 用户在数据库的id
	private String id;
	// 数据库里面的名字
	private String name;

	/**
	 * 
	 * @param username              登录名
	 * @param password              密码
	 * @param enabled               是否激活
	 * @param accountNonExpired     账户是否未过期
	 * @param credentialsNonExpired 密码是否未过期
	 * @param accountNonLocked      账户是否未锁定
	 * @param authorities           集合，用户具有的角色、身份。我们在角色的时候有KEY，通常在KEY前面加上ROLE_即可。
	 */
	public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public UserDetails(User user, Collection<? extends GrantedAuthority> authorities) {

		super(user.getLoginName(),
				user.getPassword(),
				user.getStatus() == user.getStatus().NORMAL,
				user.getStatus() != user.getStatus().DISABLE,
				user.getStatus() != user.getStatus().EXPIRED,
				user.getStatus() != user.getStatus().EXPIRED, authorities);
		this.id = user.getId();
		this.name = user.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
