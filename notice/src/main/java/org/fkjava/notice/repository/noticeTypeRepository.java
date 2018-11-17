package org.fkjava.notice.repository;

import org.fkjava.notice.domain.NoticeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface noticeTypeRepository extends JpaRepository<NoticeType, String> {

	NoticeType findByName(String name);

}
