package org.fkjava.notice.controller;

import java.util.List;

import org.fkjava.notice.domain.NoticeType;
import org.fkjava.notice.service.NoticeTypeService;
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
@RequestMapping("/notice/type")
public class NoticeTypeController {

	@Autowired
	private NoticeTypeService noticeTypeService;

	@GetMapping
	public ModelAndView show() {
		ModelAndView view = new ModelAndView("notice/type/type_index");
		List<NoticeType> types=noticeTypeService.show();
		view.addObject("types",types);
		return view;
	}

	@PostMapping("checked")
	@ResponseBody // 会转换成json或则xml
	public Result chckedNoticeTypeName(@RequestParam("name") String name, Model model) {
		Result result = noticeTypeService.chckedNoticeTypeName(name);
		return result;
	}

	@PostMapping
	public String save(NoticeType notice) {
		noticeTypeService.save(notice);
		return "redirect:/notice/type";
	}
	@DeleteMapping("{id}")
	@ResponseBody
	public String delete(@PathVariable("id")String id) {
		noticeTypeService.deleteById(id);
		return "ok";
	}
}
