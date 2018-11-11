package org.fkjava.identity.service;

import org.fkjava.identity.domain.User;
import org.fkjava.vo.Result;
import org.springframework.data.domain.Page;

public interface IdentityService {
	void save(User user);

	Result   checkLoginName(String loginName);

	Page<User> show(int number, String keyword);

	User findUserById(String id);

	void active(String id);

	void disable(String id);
}
