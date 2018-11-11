package org.fkjava.identity.controller;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.service.IdentityService;
import org.fkjava.identity.service.RoleService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/identity/show")
public class UserController {

	@Autowired
	private IdentityService identityService;
	@Autowired
	private RoleService roleService;

	@GetMapping
	public ModelAndView show(@RequestParam(name = "pageNumber", defaultValue = "0") int number, //
			@RequestParam(name = "keyword", required = false) String keyword) {

		ModelAndView view = new ModelAndView("identity/user/user_index");
		Page<User> page = identityService.show(number, keyword);
		view.addObject("page", page);
		return view;
	}

	@GetMapping("add")
	@ResponseBody
	public List<Role> add() {
		List<Role> roles = roleService.findAllNotFixed();
		return roles;
	}

	@PostMapping("/checked")
	@ResponseBody // 会转换成json或则xml
	public Result checkLoginName(@RequestParam("loginName") String loginName, Model model) {

		Result result = identityService.checkLoginName(loginName);
		return result;
	}

	@PostMapping
	public ModelAndView save(User user) {

		identityService.save(user);
		// 用户保存成功
		ModelAndView view = new ModelAndView("redirect:/identity/show");
		view.addObject("success", "保存成功");
		return view;
	}

	/**
	 * 激活
	 */
	@GetMapping("/active/{id}")
	public ModelAndView active(@PathVariable("id") String id) {

		ModelAndView view = new ModelAndView("redirect:/identity/show");
		identityService.active(id);
		return view;
	}

	/**
	 * 禁用
	 */
	@GetMapping("/disable/{id}")
	public ModelAndView disable(@PathVariable("id") String id) {

		ModelAndView view = new ModelAndView("redirect:/identity/show");
		identityService.disable(id);
		return view;
	}

	@GetMapping("/edit/{id}")
	@ResponseBody
	public User edit(@PathVariable("id") String id, Model model) {

		List<Role> unFixedRole = this.add();
		User user = identityService.findUserById(id);
		user.setUnFixedRole(unFixedRole);
		return user;
	}
}
