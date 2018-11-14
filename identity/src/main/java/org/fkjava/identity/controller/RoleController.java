package org.fkjava.identity.controller;

import java.util.List;

import org.fkjava.identity.domain.Role;
import org.fkjava.identity.service.RoleService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/identity/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping
	public ModelAndView show() {
		ModelAndView view = new ModelAndView("identity/role/role_index");
		List<Role> roles=roleService.show();
		view.addObject("roles",roles);
		return view;
	}

	@PostMapping("/checked")
	@ResponseBody // 会转换成json或则xml
	public Result chckedRoleKey(@RequestParam("roleKey") String roleKey, Model model) {
		Result result = roleService.chckedRoleKey(roleKey);
		return result;
	}

	@PostMapping
	public String save(Role role) {
		
		roleService.save(role);
		
		return "redirect:/identity/role";
	}
	@DeleteMapping("{id}")
	@ResponseBody
	public String delete(@PathVariable("id")String id) {
		roleService.deleteById(id);
		return "ok";
	}
}