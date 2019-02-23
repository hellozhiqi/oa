package cn.com.gczj.notice.service;

import java.util.List;

import cn.com.gczj.notice.domain.NoticeType;
import cn.com.gczj.vo.Result;

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
