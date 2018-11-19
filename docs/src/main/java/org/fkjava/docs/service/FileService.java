package org.fkjava.docs.service;

import java.io.InputStream;
import java.util.List;

import org.fkjava.docs.domain.FileInfo;
import org.fkjava.identity.domain.User;
import org.fkjava.vo.Result;
import org.springframework.data.domain.Page;

public interface FileService {

	/**
	 * 获取文件信息
	 * @param id
	 * @return 文件信息
	 */
	FileInfo findById(String id);

	/**
	 * 获取文件流
	 * @param fileInfo
	 * @return 流
	 */
	InputStream getFileContent(FileInfo fileInfo);

	/**
	 * 展示文件列表
	 * @param number
	 * @param keyword
	 * @return 封装有文件信息的分页对象
	 */
	Page<FileInfo> show(int number, String keyword);

	/**
	 * 删除文件
	 * @param id
	 * @return Result
	 */
	Result delect(String id);

	/**
	 * 保存文件
	 * @param info
	 * @param in
	 */
	void save(FileInfo info, InputStream in);

}
