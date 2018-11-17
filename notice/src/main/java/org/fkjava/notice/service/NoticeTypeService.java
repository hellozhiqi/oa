package org.fkjava.notice.service;

import java.util.List;

import org.fkjava.notice.domain.NoticeType;
import org.fkjava.vo.Result;

public interface NoticeTypeService {

	void save(NoticeType noticeType);

	Result chckedNoticeTypeName(String noticeTypeName);

	List<NoticeType> show();

	void deleteById(String id);
}
