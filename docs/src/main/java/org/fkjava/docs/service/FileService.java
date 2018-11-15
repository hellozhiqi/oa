package org.fkjava.docs.service;

import java.io.InputStream;
import java.util.List;

import org.fkjava.docs.domain.FileInfo;
import org.fkjava.identity.domain.User;

public interface FileService {

	void save(User user, String name, String contentType, long fileSize, InputStream in);


	FileInfo findById(String id);

	InputStream getFileContent(FileInfo fileInfo);


	List<FileInfo> findAll();


}
