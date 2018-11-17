package org.fkjava.notice.service.impl;

import java.util.List;

import org.fkjava.notice.domain.NoticeType;
import org.fkjava.notice.repository.noticeTypeRepository;
import org.fkjava.notice.service.NoticeTypeService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Service
public class NoticeTypeServiceImpl implements NoticeTypeService {

	@Autowired
	private noticeTypeRepository noticeTypeRepository;
	
	@Override
	public void save(NoticeType noticeType) {
		 
		NoticeType oa_noticeType=	this.noticeTypeRepository.findByName(noticeType.getName());
		if(oa_noticeType==null ||oa_noticeType.getId().equals(noticeType.getId())) {
			//新增、或修改
			this.noticeTypeRepository.save(noticeType);
		}
		
	}

	@Override
	public Result chckedNoticeTypeName(String noticeTypeName) {
		
		NoticeType oa_noticeType=	this.noticeTypeRepository.findByName(noticeTypeName);
		if(oa_noticeType !=null) {
			return Result.of(Result.STATUS_ERROR);
		}
		return Result.of(Result.STATUS_OK);
	}

	@Override
	public List<NoticeType> show() {
		
		Sort sort = Sort.by("name");
		return this.noticeTypeRepository.findAll(sort);
	}

	@Override
	public void deleteById(String id) {
	
		this.noticeTypeRepository.deleteById(id);
	}
}
