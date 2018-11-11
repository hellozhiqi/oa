package org.fkjava.identity.service.impl;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.identity.repository.RoleRepository;
import org.fkjava.identity.service.RoleService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RoleServiceImpl implements RoleService, InitializingBean {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void afterPropertiesSet() throws Exception {

		Role root = roleRepository.findByRoleKey("ROOT").orElse(new Role());
		root.setRoleName("超级管理员");
		root.setRoleKey("ROOT");
		this.save(root);

		Role user = roleRepository.findByRoleKey("USER").orElse(new Role());
		user.setRoleName("普通用户");
		user.setRoleKey("USER");
		user.setFixed(true);// 固定每个用户都拥有角色身份
		this.save(user);

	}

	@Override
	public void save(Role role) {

		if (StringUtils.isEmpty(role.getId())) {
			role.setId(null);
		}
		// 根据roleKey,查找Role
		Role oa_role = this.roleRepository.findByRoleKey(role.getRoleKey()).orElse(null);

		if (role.getId() == null && oa_role == null) {// 新增
			roleRepository.save(role);
		}
		if (role.getId() != null && oa_role != null && role.getId().equals(oa_role.getId())
				|| role.getId() != null && oa_role == null) { // 修改
			roleRepository.save(role);
		}
	}

	/**
	 * 检查roleKey是否唯一
	 */
	@Override
	public Result chckedRoleKey(String roleKey) {

		// 根据roleKey,查找Role
		Role oa_role = roleRepository.findByRoleKey(roleKey).orElse(null);
		if (oa_role != null) {
			return Result.of(Result.STATUS_ERROR);
		}
		return Result.of(Result.STATUS_OK);
	}

	@Override
	public List<Role> show() {

		List<Role> roles = roleRepository.findAll();
		return roles;
	}

	@Override
	public void deleteById(String id) {
		roleRepository.deleteById(id);
	}

	@Override
	public List<Role> findAllNotFixed() {
		List<Role> roles = roleRepository.findByFixedFalse();
		return roles;
	}

	@Override
	public List<Role> findAll() {
		List<Role> roles = roleRepository.findAll();
		return roles;
	}
}
