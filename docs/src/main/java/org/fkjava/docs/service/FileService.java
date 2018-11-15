package org.fkjava.docs.service;

import java.io.InputStream;
import java.util.List;

import org.fkjava.docs.domain.FileInfo;
import org.fkjava.identity.domain.User;
import org.springframework.data.domain.Page;

public interface FileService {

	void save(User user, String name, String contentType, long fileSize, InputStream in);

	FileInfo findById(String id);

	InputStream getFileContent(FileInfo fileInfo);

	Page<FileInfo> show(int number, String keyword);

}
