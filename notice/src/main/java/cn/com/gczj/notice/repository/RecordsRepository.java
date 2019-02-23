package cn.com.gczj.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.com.gczj.identity.domain.User;
import cn.com.gczj.notice.domain.Notice;
import cn.com.gczj.notice.domain.ReadingRecords;

@Repository
public interface RecordsRepository extends JpaRepository<ReadingRecords, String> {

	@Query("select new ReadingRecords(rr.id, rr.readTime, n)  "//
			+ " from Notice n  left outer join ReadingRecords rr on rr.notice=n and rr.user=:user "//
			+ " where (n.author=:author and (n.status='DRAFT' or n.status='RECALL') "//
			+ "or n.status='RELEASE') "//
			+ "order by case n.status when 'DRAFT' then 0 "//
			+ " when 'RECALL' then 99 " //
			+ " when 'RELEASE' then 1 end asc ,n.releaseTime  desc ")
	Page<ReadingRecords> findNotice(@Param("author") User author, //
			@Param("user") User user, Pageable pageable);

	ReadingRecords findByNoticeAndUser(Notice notice, User user);
	
}
