package cn.com.gczj.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class SecurityController {

	@GetMapping("/")
	public String findIndex() {
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		
		ModelAndView view=new ModelAndView("security/index");
		return view;
	}
	
	//编辑首页
	@GetMapping("/edit")
	public ModelAndView edit() {
		
		ModelAndView view=new ModelAndView("security/edit_index");
		return view;
	}
}
