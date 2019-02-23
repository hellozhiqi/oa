package cn.com.gczj.hr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.gczj.hr.domain.Dept;
import cn.com.gczj.hr.service.DeptService;
import cn.com.gczj.identity.domain.User;
import cn.com.gczj.identity.service.IdentityService;
import cn.com.gczj.vo.Result;

@Controller
@RequestMapping("/dept")
public class DepartmentController {

	@Autowired
	private DeptService deptService;
	@Autowired
	private IdentityService identityService;

	/**
	 * 查找部门视图
	 * 
	 * @return
	 */
	@GetMapping
	public ModelAndView index(@RequestParam(name = "pageNumber", defaultValue = "0") int number, //
			@RequestParam(name = "keyword", required = false) String keyword) {
		
		ModelAndView view = new ModelAndView("dept/dept_index");

		Page<User> page = this.identityService.show(number, keyword);
		view.addObject("page", page);

		return view;
	}

	/**
	 * 保存部门
	 * 
	 * @param dept
	 * @return
	 */
	@PostMapping
	public String save(Dept dept) {
		this.deptService.save(dept);
		return "redirect:/dept";
	}

	/**
	 * 查询一级部门
	 */
	@GetMapping(produces = "application/json")
	@ResponseBody
	public List<Dept> findTopDept() {
		return this.deptService.findTopDept();
	}

	/**
	 * 拖拽菜单
	 */
	@PostMapping("move")
	@ResponseBody
	public Result move(String id, String targetId, String moveType) {
		return this.deptService.move(id, targetId, moveType);
	}
	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	@ResponseBody
	public Result delete(@PathVariable("id") String id) {
		return this.deptService.delete(id);
	}
}
