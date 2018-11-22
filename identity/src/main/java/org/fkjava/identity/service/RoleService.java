package org.fkjava.identity.service;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.vo.Result;

public interface RoleService {

	/**
	 * 保存角色
	 * @param role
	 */
	void save(Role role);

	/**
	 * 检查roleKey是否唯一
	 * @param roleKey
	 * @return Result
	 */
	Result chckedRoleKey(String roleKey);

	/**
	 * 展示角色列表
	 * @return List<Role> 
	 */
	List<Role> show();

	/**
	 * 删除角色
	 * @param id
	 */
	void deleteById(String id);

	/**
	 * 查询所有不固定的角色
	 * @return List<Role>
	 */
	List<Role> findAllNotFixed();

	/**
	 * 查询所有角色
	 * 
	 * @return List<Role>
	 */
	List<Role> findAll();

}
