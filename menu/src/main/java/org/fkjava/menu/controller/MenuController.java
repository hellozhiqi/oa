package org.fkjava.menu.controller;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.service.RoleService;
import org.fkjava.identity.util.UserHoder;
import org.fkjava.menu.domain.Menu;
import org.fkjava.menu.service.MenuService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

	/**
	 * 展示菜单页面
	 * 
	 * @return
	 */
	@GetMapping
	public ModelAndView index() {

		ModelAndView view = new ModelAndView("menu/menu_index");
		List<Role> roles = roleService.findAll();
		view.addObject("roles", roles);

		return view;
	}

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 */
	@PostMapping
	public String save(Menu menu) {

		menuService.save(menu);
		return "redirect:/menu";
	}

	/**
	 * 查找一级菜单（父级）
	 */
	@GetMapping(produces = "application/json")
	@ResponseBody
	public List<Menu> findTopMenu() {
		return this.menuService.findTopMenu();
	}

	/**
	 * 拖拽菜单
	 * @param id
	 * @param targetId
	 * @param moveType
	 * @return
	 */
	@PostMapping("move")
	@ResponseBody
	public Result move(String id, String targetId, String moveType) {
		System.out.println("id=" + id + "==== targetId=" + targetId);
		return menuService.move(id, targetId, moveType);
	}

	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ResponseBody
	public Result delect(@PathVariable("id") String id) {

		return this.menuService.delect(id);
	}

	/**
	 * 查找所有菜单
	 * @return
	 */
	@GetMapping(value = "menu", produces = "application/json")
	@ResponseBody
	public List<Menu> findMenus() {

		return menuService.findMenus();
	}
}
