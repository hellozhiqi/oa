package cn.com.gczj.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.com.gczj.notice.domain.NoticeType;

@Repository
public interface noticeTypeRepository extends JpaRepository<NoticeType, String> {

	NoticeType findByName(String name);

}
