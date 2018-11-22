package org.fkjava.notice.service;

import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.ReadingRecords;
import org.fkjava.vo.Result;
import org.springframework.data.domain.Page;

public interface NoticeService {

	/**
	 * 直接发布
	 * @param notice
	 */
	void publish(Notice notice);

	/**
	 * 存草稿
	 * @param notice
	 */
	void toDraft(Notice notice);

	/**
	 * 查找公告
	 * @param number
	 * @param keyword
	 * @return 分页对象
	 */
	Page<ReadingRecords> findNotice(Integer number, String keyword);

	/**
	 * 删除公告
	 * @param id
	 * @return
	 */
	Result delete(String id);
	/**
	 * 修改公告
	 * @param id
	 * @return 
	 */
	Notice editNotice(String id);
	
	/**
	 * 发布公告
	 * @param id
	 */
	void publishNotice(String id);

	/**
	 * 撤回公告
	 * @param id
	 */
	void recallNotice(String id);

	/**
	 * 已阅读公告
	 * @param id
	 */
	Result readedNotice(String id);

	/**
	 * 查看公告
	 * @param id
	 * @return
	 */
	Notice lookNotice(String id);

}
