package cn.com.gczj.identity.controller;

import java.util.List;

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

import cn.com.gczj.identity.domain.Role;
import cn.com.gczj.identity.service.RoleService;
import cn.com.gczj.vo.Result;

@Controller
@RequestMapping("/identity/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 展示角色列表
	 */
	@GetMapping
	public ModelAndView show() {
		ModelAndView view = new ModelAndView("identity/role/role_index");
		List<Role> roles=roleService.show();
		view.addObject("roles",roles);
		return view;
	}

	/**
	 * 检查RoleKey是否唯一
	 * @param roleKey
	 * @param model
	 */
	@PostMapping("/checked")
	@ResponseBody // 会转换成json或则xml
	public Result chckedRoleKey(@RequestParam("roleKey") String roleKey, Model model) {
		Result result = roleService.chckedRoleKey(roleKey);
		return result;
	}

	/**
	 * 保存角色
	 * @param role
	 */
	@PostMapping
	public String save(Role role) {
		
		roleService.save(role);
		
		return "redirect:/identity/role";
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ResponseBody
	public String delete(@PathVariable("id")String id) {
		roleService.deleteById(id);
		return "ok";
	}
}
