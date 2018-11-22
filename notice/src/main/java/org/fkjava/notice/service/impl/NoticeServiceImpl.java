package org.fkjava.notice.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.fkjava.identity.domain.User;
import org.fkjava.identity.util.UserHoder;
import org.fkjava.notice.domain.Notice;
import org.fkjava.notice.domain.Notice.Status;
import org.fkjava.notice.domain.ReadingRecords;
import org.fkjava.notice.repository.NoticeRespsitory;
import org.fkjava.notice.repository.RecordsRepository;
import org.fkjava.notice.service.NoticeService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRespsitory noticeRespsitory;
	@Autowired
	private RecordsRepository recordsRepository;

	/**
	 * 编辑直接发布
	 */
	@Override
	public void publish(Notice notice) {

		if (StringUtils.isEmpty(notice.getId())) {
			notice.setId(null);
		}
		// 填充字段
		fillFiled(notice, Status.RELEASE);// 已发布
		// 保存数据
		this.noticeRespsitory.save(notice);
	}

	/**
	 * 存放草稿
	 */
	@Override
	public void toDraft(Notice notice) {

		if (StringUtils.isEmpty(notice.getId())) {
			notice.setId(null);
		}
		// 填充字段
		fillFiled(notice, Status.DRAFT);// 存草稿
		// 保存数据
		this.noticeRespsitory.save(notice);
	}

	/**
	 * 填充字段
	 * 
	 * @param notice 公告
	 * @param status 状态
	 */
	public void fillFiled(Notice notice, Status status) {

		notice.setReleaseTime(new Date());
		notice.setWriteTime(new Date());
		notice.setAuthor(UserHoder.get());
		notice.setStatus(status);// 状态
	}

	/**
	 * 查找公告
	 */
	@Override
	public Page<ReadingRecords> findNotice(Integer number, String keyword) {

		Pageable pageable = PageRequest.of(number, 10);
		User author = UserHoder.get();
		// 根据用户关联阅读记录表,根据用户查询公告状态
		Page<ReadingRecords> dataPage = this.noticeRespsitory.findNotice(author, author, pageable);
		List<ReadingRecords> content = dataPage.getContent();
		Page<ReadingRecords> page = new PageImpl<>(content, pageable, dataPage.getTotalElements());

		return page;
	}

	/**
	 * 删除公告
	 */
	@Override
	public Result delete(String id) {
		
		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		if(notice !=null) {
			this.noticeRespsitory.delete(notice);
		}
		return Result.of(Result.STATUS_OK);
	}

	/**
	 * 编辑公告
	 * @return 
	 */
	@Override
	public Notice editNotice(String id) {
		return this.noticeRespsitory.findById(id).orElse(null);
	}

	/**
	 * [列表] 发布公告
	 */
	@Override
	@Transactional
	public void publishNotice(String id) {

		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		if(notice!=null) {
			notice.setStatus(Status.RELEASE);
			notice.setReleaseTime(new Date());
		}
	}

	/**
	 * 撤回公告
	 */
	@Override
	@Transactional
	public void recallNotice(String id) {
		
		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		if(notice!=null) {
			notice.setStatus(Status.RECALL);
		}
	}

	/**
	 * 已阅读公告
	 * @return 
	 */
	@Override
	@Transactional
	public Result readedNotice(String id) {
		
		User user=UserHoder.get();
		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		ReadingRecords oa_records = this.recordsRepository.findByNoticeAndUser(notice,user);
		if(oa_records==null) {
			
			ReadingRecords records=new ReadingRecords();
			records.setNotice(notice);
			records.setReadTime(new Date());
			records.setUser(user);
			//保存数据
			this.recordsRepository.save(records);
			
			return Result.of(Result.STATUS_OK);
		}
		return Result.of(Result.STATUS_ERROR);
		
	}

	/**
	 * 查看公告
	 */
	@Override
	public Notice lookNotice(String id) {
		return this.noticeRespsitory.findById(id).orElse(null);
	}
}
