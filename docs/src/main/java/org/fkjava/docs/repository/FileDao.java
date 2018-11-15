package org.fkjava.docs.repository;

import org.fkjava.docs.domain.FileInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDao extends JpaRepository<FileInfo, FileInfo> {

	FileInfo findById(String id);

	Page<FileInfo> findByNameContaining(String keyword, Pageable pageable);

}
