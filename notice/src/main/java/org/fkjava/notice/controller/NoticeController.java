package org.fkjava.notice.controller;

import java.util.List;

import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.NoticeType;
import org.fkjava.notice.domain.ReadingRecords;
import org.fkjava.notice.service.NoticeService;
import org.fkjava.notice.service.NoticeTypeService;
import org.fkjava.vo.Result;
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

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeTypeService noticeTypeService;

	/**
	 * 展示公告列表
	 * @param number
	 * @param keyword
	 */
	@GetMapping
	public ModelAndView index(@RequestParam(name = "pageNumber", defaultValue = "0") Integer number, //
			@RequestParam(name = "keyword", required = false) String keyword) {

		ModelAndView view = new ModelAndView("notice/index");
		Page<ReadingRecords> page = this.noticeService.findNotice(number, keyword);
		view.addObject("page", page);

		return view;
	}

	/**
	 * 添加公告
	 */
	@GetMapping("add")
	public ModelAndView add() {

		ModelAndView view = new ModelAndView("notice/add");
		List<NoticeType> types = noticeTypeService.show();
		view.addObject("types", types);
		return view;
	}

	/**
	 * 发布[编辑富文本下的发布按钮]
	 * 
	 * @param notice
	 */
	@PostMapping("send")
	public ModelAndView publish(Notice notice) {

		ModelAndView view = new ModelAndView("redirect:/notice");
		this.noticeService.publish(notice);
		return view;
	}

	/**
	 * 存放草稿
	 */
	@PostMapping("toDraft")
	public ModelAndView toDraft(Notice notice) {

		ModelAndView view = new ModelAndView("redirect:/notice");
		this.noticeService.toDraft(notice);
		return view;
	}

	/**
	 * 删除公告
	 */
	@DeleteMapping("delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable("id") String id) {

		Result result = this.noticeService.delete(id);
		return result;
	}

	/**
	 * 修改公告
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("edit/{id}")
	public ModelAndView editNotice(@PathVariable("id") String id) {

		ModelAndView view = new ModelAndView("notice/add");
		Notice notice = this.noticeService.editNotice(id);
		List<NoticeType> types = noticeTypeService.show();

		view.addObject("notice", notice);
		view.addObject("types", types);

		return view;
	}

	/**
	 * 发布公告
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("publish/{id}")
	public ModelAndView publishNotice(@PathVariable("id") String id) {

		ModelAndView view = new ModelAndView("redirect:/notice");
		this.noticeService.publishNotice(id);
		return view;
	}

	/**
	 * 撤回公告
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("recall/{id}")
	public ModelAndView recallNotice(@PathVariable("id") String id) {

		ModelAndView view = new ModelAndView("redirect:/notice");
		this.noticeService.recallNotice(id);
		return view;
	}

	/**
	 * 查看公告
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("read/{id}")
	public ModelAndView readNotice(@PathVariable("id") String id) {

		ModelAndView view = new ModelAndView("notice/read");
		Notice notice = this.noticeService.lookNotice(id);
		view.addObject("notice", notice);

		return view;
	}

	/**
	 * 以阅读公告
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("readed/{id}")
	@ResponseBody
	public Result readedNotice(@PathVariable("id") String id) {

		Result result = this.noticeService.readedNotice(id);

		return result;
	}
}
