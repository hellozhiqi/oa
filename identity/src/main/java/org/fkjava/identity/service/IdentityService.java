package org.fkjava.identity.service;

import java.util.Optional;

import org.fkjava.identity.domain.User;
import org.fkjava.vo.Result;
import org.springframework.data.domain.Page;

public interface IdentityService {
	/**
	 * 保存用户
	 * @param user
	 */
	void save(User user);

	/**
	 * 检查登录名是否唯一
	 * @param loginName
	 * @return Result
	 */
	Result   checkLoginName(String loginName);

	/**
	 * 展示用户列表
	 * @param number
	 * @param keyword
	 * @return
	 */
	Page<User> show(int number, String keyword);

	/**
	 * 修改用户
	 * @param id
	 * @return User
	 */
	User findUserById(String id);

	/**
	 * 激活账户
	 * @param id
	 */
	void active(String id);

	/**
	 * 禁用账户
	 * @param id
	 */
	void disable(String id);

	/**
	 * 
	 * @param username
	 * @return
	 */
	Optional<User> findByLoginName(String username);
}
