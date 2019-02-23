package cn.com.gczj.layout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("layout")
public class IndexController {

	/**
	 * 展示统一布局页面
	 * @return
	 */
	@GetMapping
	public ModelAndView index() {

		ModelAndView view = new ModelAndView("layout/index");
		return view;
	}
	
	/**
	 * 统一处理错误页面
	 */
	@RequestMapping("error")
	public String error() {
		return "layout/error";
	}
}
