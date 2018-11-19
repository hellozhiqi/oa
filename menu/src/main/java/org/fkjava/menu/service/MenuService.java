package org.fkjava.menu.service;

import java.util.List;

import org.fkjava.menu.domain.Menu;
import org.fkjava.vo.Result;

public interface MenuService {

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 */
	void save(Menu menu);

	/**
	 * 查找一级菜单（父级）
	 * 
	 * @return List<Menu>
	 */
	List<Menu> findTopMenu();

	/**
	 * 拖拽菜单
	 * 
	 * @param id
	 * @param targetId
	 * @param moveType
	 * @return Result
	 */
	Result move(String id, String targetId, String moveType);

	/**
	 * 删除菜单
	 * 
	 * @param id
	 * @return Result
	 */
	Result delect(String id);

	/**
	 * 根据用户的角色查询菜单
	 * 
	 * @return
	 */
	List<Menu> findMenus();
}
