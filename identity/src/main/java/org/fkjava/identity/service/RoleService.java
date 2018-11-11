package org.fkjava.identity.service;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.vo.Result;

public interface RoleService {

	void save(Role role);

	Result chckedRoleKey(String roleKey);

	List<Role> show();

	void deleteById(String id);

	List<Role> findAllNotFixed();

	List<Role> findAll();

}
