package cn.com.gczj.menu.service.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.gczj.identity.domain.Role;
import cn.com.gczj.identity.domain.User;
import cn.com.gczj.identity.repository.RoleRepository;
import cn.com.gczj.identity.repository.UserRepository;
import cn.com.gczj.menu.domain.Menu;
import cn.com.gczj.menu.repository.MenuRepository;
import cn.com.gczj.menu.service.MenuService;
import cn.com.gczj.vo.Result;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;

	/**
	 * 保存菜单
	 */
	@Override
	public void save(Menu menu) {

		if (StringUtils.isEmpty(menu.getId())) {
			menu.setId(null);
		}
		if (menu.getParent() != null && StringUtils.isEmpty(menu.getParent().getId())) {
			// 上级菜单为null,没有上级菜单
			menu.setParent(null);
		}
		// 1.在同一菜单,不能有相同的子菜单
		Menu oa_menu;
		if (menu.getParent() != null) {// 有上级菜单
			oa_menu = menuRepository.findByNameAndParent(menu.getName(), menu.getParent());
		} else {// 没有上级菜单
			oa_menu = menuRepository.findByNameAndParentNull(menu.getName());
		}
		// 2.根据选取的角色id,查询角色
		List<String> rolesIds = new LinkedList<>();
		if (menu.getRoles() == null) {
			menu.setRoles(new LinkedList<>());
		}
		menu.getRoles().forEach(role -> rolesIds.add(role.getId()));
		List<Role> roles = roleRepository.findAllById(rolesIds);
		Set<Role> set = new HashSet<>();
		set.addAll(roles); // 去重

		menu.getRoles().clear();
		menu.getRoles().addAll(set);

		// 3.设置排列序号(菜单可以拖拽)
		// 找到同级最大的number，然后加10000000，就形成一个新的number作为当前菜单的number
		if (oa_menu != null) {
			menu.setNumber(oa_menu.getNumber());
		} else {
			Double maxNumber;
			if (menu.getParent() == null) {
				maxNumber = menuRepository.findMaxNumberByParentNull();
			} else {
				maxNumber = menuRepository.findMaxNumberByParent(menu.getParent());
			}
			if (maxNumber == null) {
				maxNumber = 0.0;
			}
			Double number = maxNumber + 100000.0;
			menu.setNumber(number);
		}
		// 4.保存数据
		menuRepository.save(menu);
	}

	/**
	 * 查找一级菜单（父级）
	 */
	@Override
	public List<Menu> findTopMenu() {
		return this.menuRepository.findByParentNullOrderByNumber();
	}

	/**
	 * 拖拽菜单
	 */
	@Override
	@Transactional
	public Result move(String id, String targetId, String moveType) {

		// 当同菜单，移动相同的子菜单情况下，返回错误
		if (StringUtils.isEmpty(id)) {
			return Result.of(Result.STATUS_ERROR);
		}

		Menu menu = this.menuRepository.findById(id).orElse(null);

		// 当菜单从有父菜单拖拽到外面，没有父菜单的情况下
		if (StringUtils.isEmpty(targetId)) {

			Double maxNumber = this.menuRepository.findMaxNumberByParentNull();
			if (maxNumber == null) {
				maxNumber = 0.0;
			}
			menu.setNumber(maxNumber);
			menu.setParent(null);
			return Result.of(Result.STATUS_OK);
		}
		Menu target = this.menuRepository.findById(targetId).orElse(null);

		if ("inner".equals(moveType)) {

			for (Menu child : target.getChilds()) {
				if (child.getName().equals(menu.getName())) {
					return Result.of(Result.STATUS_ERROR);
				}
			}
			Double maxNumber = this.menuRepository.findMaxNumberByParent(target);
			if (maxNumber == null) {
				maxNumber = 0.0;
			}
			Double number = maxNumber + 100000.0;
			menu.setNumber(number);
			menu.setParent(target);
		} else if ("prev".equals(moveType)) {
			// prev 拖拽到target之前,但menu的number要比target的前一个菜单number大即可
			// 查询第一页,只要第一条数据
			PageRequest pageable = PageRequest.of(0, 1);
			// 通过目标target,传入target的prarent,以及target的number,
			// 返回小于target number,封装到Page<Menu>对象的menu,[使用倒序查询,取第一个]
			Page<Menu> prevs = this.menuRepository.findByParentAndNumberLessThanOrderByNumberDesc(target.getParent(),
					target.getNumber(), pageable);

			Double next = target.getNumber();
			Double number;
			if (prevs.getNumberOfElements() > 0) {
				Double prev = prevs.getContent().get(0).getNumber();// 获取紧跟target的一个menu的number
				number = (next + prev) / 2;
			} else {
				number = next / 2;
			}
			menu.setNumber(number);
			// 移动到target之前，跟target同级
			menu.setParent(target.getParent());
		} else if ("next".equals(moveType)) {
			// next 拖拽到target之后,menu的number要比target菜单的number大,但要比target后面紧跟菜单的number小即可
			// 查询第一页,只要第一条数据
			Pageable pageable = PageRequest.of(0, 1);
			// 通过目标target,传入target的prarent,以及target的number,
			// 返回大于target number,封装到Page<Menu>对象的menu[使用升序,取第一个]
			Page<Menu> prevs = this.menuRepository.findByParentAndNumberGreaterThanOrderByNumberAsc(target.getParent(),
					target.getNumber(), pageable);

			Double prev = target.getNumber();
			Double number;
			if (prevs.getNumberOfElements() > 0) {
				Double next = prevs.getContent().get(0).getNumber(); // 获取紧跟target的下一个menu的number
				number = (next + prev) / 2;
			} else {
				number = prev + 100000.0;
			}
			menu.setNumber(number);
			// 移动到target之后，跟target同级
			menu.setParent(target.getParent());
		} else {
			throw new IllegalArgumentException("非法的菜单移动类型，只允许inner、prev、next三选一。");
		}

		return Result.of(Result.STATUS_OK);
	}

	/**
	 * 删除菜单
	 */
	@Override
	public Result delect(String id) {

		Menu menu = this.menuRepository.findById(id).orElse(null);
		if (menu != null) {
			if (menu.getChilds().isEmpty()) {
				this.menuRepository.delete(menu);
			} else {
				return Result.of(Result.STATUS_ERROR);
			}
		}
		return Result.of(Result.STATUS_OK);
	}

	/**
	 * 根据用户的角色查询菜单
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Menu> findMyMenus(String id) {

		User user = userRepository.getOne(id);
		// 1.根据roles集合,查询所有的所有菜单,得到用户用户有权限访问的菜单
		List<Role> roles = user.getRoles();
		List<Menu> menus = this.menuRepository.findDistinctByRolesIn(roles);
		// 返回一级菜单
		List<Menu> topMenu = new LinkedList<>();
		Map<Menu, List<Menu>> map = new HashMap<>();

		// 2.构架菜单与下级的关系
		menus.stream()
				// 过滤掉没有子节点的菜单
				.filter(menu -> menu.getParent() != null).forEach(menu -> {
					// 得到上级(父菜单)
					Menu parent = menu.getParent();
					if (!map.containsKey(parent)) {
						map.put(parent, new LinkedList<>());
					}
					// 得到子菜单
					List<Menu> childs = map.get(parent);
					childs.add(menu);
				});
		// 使用排序器,在内存里面对菜单进行排序,以序号为依据
		Comparator<Menu> comparator = (menu1, menu2) -> {
			if (menu1.getNumber() > menu2.getNumber()) {
				return 1;
			} else if (menu1.getNumber() < menu2.getNumber()) {
				return -1;
			} else {
				return 0;
			}
		};
		// 3. 重新构建一级,二级菜单
		map.entrySet().stream()
				// 判断没有上一级,表示一级菜单
				.filter(entry -> entry.getKey().getParent() == null).forEach(entry -> {
					Menu parent = entry.getKey();// 一级菜单
					List<Menu> childs = entry.getValue();// 二级菜单
					// 查询到的menu,不要覆盖
					// 返回菜单的时候,由于通过权限组装数据,所以需要修改数据
					Menu menu = copy(parent);
					childs.forEach(child -> {
						Menu second = this.copy(child); // 二级菜单
						menu.getChilds().add(second);
					});
					menu.getChilds().sort(comparator);// 排序二级菜单
					topMenu.add(parent);
				});

		topMenu.sort(comparator);// 排序一级菜单

		return topMenu;
	}

	private Menu copy(Menu persist) {

		Menu menu = new Menu();
		menu.setId(persist.getId());
		menu.setName(persist.getName());
		menu.setNumber(persist.getNumber());
		menu.setUrl(persist.getUrl());
		menu.setChilds(new LinkedList<>());
		return menu;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<String> findMyUrls(String id) {

		User user = userRepository.getOne(id);
		List<Role> roles = user.getRoles();

		List<Menu> menus = this.menuRepository.findDistinctByRolesIn(roles);
		Set<String> urls = new HashSet<>();
		menus.forEach(menu -> {
			urls.add(menu.getUrl());
		});
		return urls;
	}
}
