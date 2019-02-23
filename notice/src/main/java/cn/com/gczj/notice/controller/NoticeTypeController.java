package cn.com.gczj.notice.controller;

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

import cn.com.gczj.notice.domain.NoticeType;
import cn.com.gczj.notice.service.NoticeTypeService;
import cn.com.gczj.vo.Result;

@Controller
@RequestMapping("/notice/type")
public class NoticeTypeController {

	@Autowired
	private NoticeTypeService noticeTypeService;

	/**
	 * 展示公告类型
	 */
	@GetMapping
	public ModelAndView show() {
		ModelAndView view = new ModelAndView("notice/type/type_index");
		List<NoticeType> types = noticeTypeService.show();
		view.addObject("types", types);
		return view;
	}

	/**
	 * 检查公告类型名称是否唯一
	 * 
	 * @param name
	 * @param model
	 */
	@PostMapping("checked")
	@ResponseBody // 会转换成json或则xml
	public Result chckedNoticeTypeName(@RequestParam("name") String name, Model model) {
		Result result = noticeTypeService.chckedNoticeTypeName(name);
		return result;
	}

	/**
	 * 保存公告类型
	 * 
	 * @param notice
	 */
	@PostMapping
	public String save(NoticeType notice) {
		noticeTypeService.save(notice);
		return "redirect:/notice/type";
	}

	/**
	 * 删除公告类型
	 * 
	 * @param id
	 */
	@DeleteMapping("{id}")
	@ResponseBody
	public String delete(@PathVariable("id") String id) {
		noticeTypeService.deleteById(id);
		return "ok";
	}
}
