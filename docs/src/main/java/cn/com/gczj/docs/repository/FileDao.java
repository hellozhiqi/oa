package cn.com.gczj.docs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.com.gczj.docs.domain.FileInfo;
import cn.com.gczj.identity.domain.User;

@Repository
public interface FileDao extends JpaRepository<FileInfo, FileInfo> {

	FileInfo findById(String id);

	Page<FileInfo> findByUser(User user, Pageable pageable);

	Page<FileInfo> findByUserAndNameContaining(User user, String keyword, Pageable pageable);

}
