package org.fkjava.notice.service;

import java.util.List;

import org.fkjava.notice.domain.NoticeType;
import org.fkjava.vo.Result;

public interface NoticeTypeService {

	/**
	 * 保存公告类型
	 * 
	 * @param noticeType
	 */
	void save(NoticeType noticeType);

	/**
	 * 检查公告类型名称
	 * 
	 * @param noticeTypeName
	 * @return Result
	 */
	Result chckedNoticeTypeName(String noticeTypeName);

	/**
	 * 展示公告类型列表
	 * @return List<NoticeType> 
	 */
	List<NoticeType> show();

	/**
	 * 删除公告类型
	 * @param id
	 */
	void deleteById(String id);
}
