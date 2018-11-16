package org.fkjava.docs.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.fkjava.docs.domain.FileInfo;
import org.fkjava.docs.repository.FileDao;
import org.fkjava.docs.service.FileService;
import org.fkjava.identity.domain.User;
import org.fkjava.identity.util.UserHoder;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FileServiceImpl implements FileService, InitializingBean {

	@Autowired
	private FileDao fileDao;
	
	String dir="/home/mia/temp";
	// 文件保存路径
	private File file = new File(dir);

	// 实现InitializingBean该接口后，在Bean完成创建并注入后，调用该方法
	@Override
	public void afterPropertiesSet() throws Exception {
		if (!file.exists()) {
			file.mkdirs();
		}
		System.out.println("文件实际存储位置：" + file.getAbsolutePath());
	}

	@Transactional
	@Override
	public void save(User user, String name, String contentType, long fileSize, InputStream in) {

		String fileName = UUID.randomUUID().toString().replace("-", "");

		System.out.println(fileName);
		System.out.println(contentType);
		System.out.println(fileSize);

		// 保存文件内容
		File file = new File(dir, fileName);
		Path target = file.toPath();
		try {
			Files.copy(in, target);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		// 保存文件
		FileInfo info = new FileInfo();
		info.setContentType(contentType);
		info.setFileName(fileName);
		info.setFileSize(fileSize);
		info.setName(name);
		info.setUploadTime(new Date());
		info.setUser(user);// 贡献者

		fileDao.save(info);
	}

	@Override
	public FileInfo findById(String id) {

		return this.fileDao.findById(id);
	}

	@Override
	public InputStream getFileContent(FileInfo fileInfo) {

		File file = new File(dir, fileInfo.getFileName());
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}

	@Override
	public Page<FileInfo> show(int number, String keyword) {

		User user=UserHoder.get();
		if (StringUtils.isEmpty(keyword)) {
			keyword = null;
		}
		// 每页10条
		Pageable pageable = PageRequest.of(number, 10);
		Page<FileInfo> page = null;
		if (keyword == null) {
			//根据用户名
			page = fileDao.findByUser(user,pageable);
		}else {
			//根据文件名，前后模糊
			page=fileDao.findByUserAndNameContaining(user,keyword,pageable);
		}
		return page;
	}

	@Override
	public Result delect(String id) {
		 
		FileInfo fileInfo = fileDao.findById(id);
		if(fileInfo!=null) {
			//从硬盘中删除文件
			File file=new File(dir,fileInfo.getFileName());
			file.delete();
			//删除数据库文件信息
			fileDao.delete(fileInfo);
			
			return Result.of(Result.STATUS_OK);
		}
		
		return  Result.of(Result.STATUS_ERROR);
	}
}
