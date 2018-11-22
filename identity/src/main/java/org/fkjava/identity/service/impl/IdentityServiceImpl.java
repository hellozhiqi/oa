package org.fkjava.identity.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.fkjava.identity.domain.Role;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.domain.User.Status;
import org.fkjava.identity.repository.RoleRepository;
import org.fkjava.identity.repository.UserRepository;
import org.fkjava.identity.service.IdentityService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class IdentityServiceImpl implements IdentityService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 保存用户
	 */
	@Override
	public void save(User user) {

		// 处理角色
		// 1.查询所有的固定角色
		List<Role> fixedRoles = roleRepository.findByFixedTrue();

		// 2.页面传入的角色
		List<Role> pageRoles = user.getRoles();
		if (pageRoles == null) {
			pageRoles = new LinkedList<>();
			user.setRoles(pageRoles);
		} else {
			// 根据页面传递过来的id,查询所有的role
			List<String> ids = new LinkedList<>();
			pageRoles.stream().map((role) -> {
				return role.getId();
			}).forEach(id -> {
				ids.add(id);
			});

			List<Role> tmp = this.roleRepository.findAllById(ids);
			pageRoles.clear();
			pageRoles.addAll(tmp);
		}
		// 3.确保角色不重复,需要重写Role的HashSet,equals方法
		Set<Role> allRoles = new HashSet<>();
		allRoles.addAll(fixedRoles);
		allRoles.addAll(pageRoles);

		// 将整理后的rolse放回User里面,pageRoles以set进去
		pageRoles.clear();
		pageRoles.addAll(allRoles);

		// 检查用户是有存在id,如果有,则把它设置为null
		if (StringUtils.isEmpty(user.getId())) {
			user.setId(null);
		}
		// 根据用户名查看数据库,判断用户名是否被占用(考虑修改)
		User oa_user = userRepository.findByLoginName(user.getLoginName());
		// 每次修改,都应该处于正常状态
		user.setStatus(Status.NORMAL);
		user.setExpiredTime(getexpiredTime());

		if (oa_user == null) { // 登录名非占用情况,新增
			String enPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(enPassword);
			userRepository.save(user);
		} else if (user.getId() != null && user.getId().equals(oa_user.getId())) {// 登录名占用的情况,做修改

			String enPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(enPassword);
			userRepository.save(user);
		} else {// 占用情况
				// throw new IllegalArgumentException("用户名以被使用!");
		}
	}

	/**
	 * 异步检查,登录名是否被占用
	 */
	@Override
	public Result checkLoginName(String loginName) {
		// 根据用户名查看数据库,判断用户名是否被占用(考虑修改)
		User oa_user = userRepository.findByLoginName(loginName);
		if (oa_user != null) { // 被占用的情况
			return Result.of(Result.STATUS_ERROR);
		}
		return Result.of(Result.STATUS_OK);
	}

	/**
	 * 展示用户列表
	 */
	@Override
	public Page<User> show(int number, String keyword) {

		Page<User> page = this.findAllUsers(keyword, number, 8);
		return page;
	}

	/**
	 * 点击修改用户
	 */
	@Override
	public User findUserById(String id) {

		User user = userRepository.findById(id).orElse(null);

		return user;
	}

	/**
	 * 激活账户
	 */
	@Override
	@Transactional
	public void active(String id) {

		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			user.setExpiredTime(getexpiredTime());
			user.setStatus(User.Status.NORMAL);
		}
	}

	/**
	 * 获取过期时间
	 */
	private Date getexpiredTime() {

		Calendar cal = Calendar.getInstance();
		int marth = cal.get(Calendar.MARCH);
		marth += 2;
		cal.set(Calendar.MARCH, marth);

		return cal.getTime();
	}

	/**
	 * 禁用账户
	 */
	@Override
	@Transactional
	public void disable(String id) {

		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			user.setStatus(User.Status.DISABLE);
		}
	}

	/**
	 * 
	 */
	@Override
	public Optional<User> findByLoginName(String username) {

		User user = userRepository.findByLoginName(username);
		Optional<User> ofNullable = Optional.ofNullable(user);

		return ofNullable;
	}

	/**
	 * 根据关键字返回用户
	 */
	@Override
	public List<User> findUsers(String keyword) {
		Page<User> page = findAllUsers(keyword, 0, 15);
		return page.getContent();
	}

	/**
	 * 相同代码
	 * 
	 * @param keyword
	 * @param number
	 * @param size
	 * @return
	 */
	private Page<User> findAllUsers(String keyword, Integer number, Integer size) {

		if (StringUtils.isEmpty(keyword)) {
			keyword = null;
		}
		// 分页条件
		Pageable pageable = PageRequest.of(number, size);
		Page<User> page;
		if (keyword == null) {
			// 分页查询所有数据
			page = userRepository.findAll(pageable);
		} else {
			// 根据姓名查询，前后模糊查询
			page = userRepository.findByNameContaining(keyword, pageable);
		}

		return page;
	}
}
