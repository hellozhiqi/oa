package cn.com.gczj.docs.service.impl;

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
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.gczj.docs.domain.FileInfo;
import cn.com.gczj.docs.repository.FileDao;
import cn.com.gczj.docs.service.FileService;
import cn.com.gczj.identity.domain.User;
import cn.com.gczj.identity.repository.UserRepository;
import cn.com.gczj.identity.service.IdentityService;
import cn.com.gczj.identity.util.UserHoder;
import cn.com.gczj.vo.Result;

@Service
public class FileServiceImpl implements FileService, InitializingBean {

	@Autowired
	private FileDao fileDao;
	@Autowired
	private UserRepository userRepository;

	String dir = "/home/mia/temp";
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

	/**
	 * 保存文件
	 */
	@Transactional
	@Override
	public void save(FileInfo info, InputStream in) {

		String fileName = UUID.randomUUID().toString().replace("-", "");

		// 保存文件内容
		File file = new File(dir, fileName);
		Path target = file.toPath();
		try {
			Files.copy(in, target);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		User user = UserHoder.get();
		user = this.userRepository.findById(user.getId()).orElse(null);

		info.setContentType(info.getContentType());
		info.setFileName(fileName);
		info.setFileSize(info.getFileSize());
		info.setName(info.getName());
		info.setUploadTime(new Date());
		info.setUser(user);// 贡献者

		FileInfo fi = fileDao.save(info);
	}

	/**
	 * 获取文件信息
	 */
	@Override
	public FileInfo findById(String id) {

		return this.fileDao.findById(id);
	}

	/**
	 * 获取文件流
	 */
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

	/**
	 * 查找文件列表的分页对象
	 */
	@Override
	public Page<FileInfo> show(int number, String keyword) {

		User user = UserHoder.get();
		if (StringUtils.isEmpty(keyword)) {
			keyword = null;
		}
		// 每页10条
		Pageable pageable = PageRequest.of(number, 10);
		Page<FileInfo> page = null;
		if (keyword == null) {
			// 根据用户名
			page = fileDao.findByUser(user, pageable);
		} else {
			// 根据文件名，前后模糊
			page = fileDao.findByUserAndNameContaining(user, keyword, pageable);
		}
		return page;
	}

	/**
	 * 从磁盘删除文件、数据库删除文件信息
	 */
	@Override
	public Result delect(String id) {

		FileInfo fileInfo = fileDao.findById(id);
		if (fileInfo != null) {
			// 从硬盘中删除文件
			File file = new File(dir, fileInfo.getFileName());
			file.delete();
			// 删除数据库文件信息
			fileDao.delete(fileInfo);

			return Result.of(Result.STATUS_OK);
		}

		return Result.of(Result.STATUS_ERROR);
	}
}
