package cn.com.gczj.notice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.com.gczj.notice.domain.NoticeType;
import cn.com.gczj.notice.repository.noticeTypeRepository;
import cn.com.gczj.notice.service.NoticeTypeService;
import cn.com.gczj.vo.Result;

@Service
public class NoticeTypeServiceImpl implements NoticeTypeService {

	@Autowired
	private noticeTypeRepository noticeTypeRepository;

	/**
	 * 保存公告类型
	 */
	@Override
	public void save(NoticeType noticeType) {

		NoticeType oa_noticeType = this.noticeTypeRepository.findByName(noticeType.getName());
		if (oa_noticeType == null || oa_noticeType.getId().equals(noticeType.getId())) {
			// 新增、或修改
			this.noticeTypeRepository.save(noticeType);
		}
	}

	/**
	 * 检查公告类型名称
	 */
	@Override
	public Result chckedNoticeTypeName(String noticeTypeName) {

		NoticeType oa_noticeType = this.noticeTypeRepository.findByName(noticeTypeName);
		if (oa_noticeType != null) {
			return Result.of(Result.STATUS_ERROR);
		}
		return Result.of(Result.STATUS_OK);
	}

	/**
	 * 展示公告类型列表
	 */
	@Override
	public List<NoticeType> show() {

		Sort sort = Sort.by("name");
		return this.noticeTypeRepository.findAll(sort);
	}

	/**
	 * 删除公告类型
	 */
	@Override
	public void deleteById(String id) {

		this.noticeTypeRepository.deleteById(id);
	}
}
