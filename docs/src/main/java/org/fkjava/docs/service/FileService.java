package org.fkjava.docs.service;

import java.io.InputStream;
import java.util.List;

import org.fkjava.docs.domain.FileInfo;
import org.fkjava.identity.domain.User;
import org.fkjava.vo.Result;
import org.springframework.data.domain.Page;

public interface FileService {

	FileInfo findById(String id);

	InputStream getFileContent(FileInfo fileInfo);

	Page<FileInfo> show(int number, String keyword);

	Result delect(String id);

	void save(FileInfo info, InputStream in);

}
