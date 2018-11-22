package org.fkjava.docs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.fkjava.docs.domain.FileInfo;
import org.fkjava.docs.service.FileService;
import org.fkjava.identity.service.IdentityService;
import org.fkjava.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("/docs")
public class FileInfoController {

	@Autowired
	private FileService fileService;
	@Autowired
	private IdentityService identityService;

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	@PostMapping("upload")
	public String upload(@RequestParam("file") MultipartFile file) {

		this.wangEditorUpload(file);

		return "redirect:/docs/show";
	}

	/**
	 * 富文本上传图片
	 * @param file
	 * @return
	 */
	@PostMapping("upload/wangEditor")
	@ResponseBody
	public WangEditorResponse wangEditorUpload(@RequestParam("file") MultipartFile file) {

		FileInfo info = new FileInfo();
		
		info.setName(file.getOriginalFilename());
		info.setContentType(file.getContentType());
		info.setFileSize(file.getSize());

		try (InputStream in = file.getInputStream();) {
			fileService.save(info, in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		WangEditorResponse wangEditorResponse = new WangEditorResponse();
		wangEditorResponse.setErrno(0);// 上传成功
		wangEditorResponse.getData().add("/docs/download/" + info.getId());

		return wangEditorResponse;
	}
	/*
	 * 富文本响应的数据类型
	 */
	public static class WangEditorResponse {

		private int errno;
		private List<String> data = new LinkedList<>();

		public int getErrno() {
			return errno;
		}

		public void setErrno(int errno) {
			this.errno = errno;
		}

		public List<String> getData() {
			return data;
		}

		public void setData(List<String> data) {
			this.data = data;
		}
	}
	/**
	 * 文件列表
	 * @param number
	 * @param keyword
	 * @return
	 */
	@GetMapping("show")
	public ModelAndView show(@RequestParam(name = "pageNumber", defaultValue = "0") int number,
			@RequestParam(name = "keyword", required = false) String keyword) {

		ModelAndView view = new ModelAndView();
		view.setViewName("download/show");

		Page<FileInfo> page = fileService.show(number, keyword);
		view.addObject("page", page);

		return view;
	}

	/**
	 * 下载
	 * @param id
	 * @param userAgent
	 * @return
	 */
	@GetMapping("/download/{abc}")
	public ResponseEntity<StreamingResponseBody> download2(@PathVariable("abc") String id,
			@RequestHeader("User-Agent") String userAgent) {

		// 读取文件信息
		FileInfo fileInfo = fileService.findById(id);
		// 获取文件流
		InputStream in = fileService.getFileContent(fileInfo);
		try {
			// 构建响应体
			BodyBuilder builder = ResponseEntity.ok();// 200 ok
			builder.contentLength(fileInfo.getFileSize());// 文件大小
			builder.contentType(MediaType.valueOf(fileInfo.getContentType()));// 文件类型
			String name = fileInfo.getName();

			// 对文件名进行编码，以防文件名乱码
			name = URLEncoder.encode(name, String.valueOf(Charset.forName("UTF-8")));
			builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + name);
			System.out.println("用户的浏览器: " + userAgent);
			StreamingResponseBody body = new StreamingResponseBody() {
				@Override
				public void writeTo(OutputStream out) throws IOException {
					byte[] bs = new byte[1024 * 1024];
					for (int len = in.read(bs); len != -1; len = in.read(bs)) {
						out.write(bs, 0, len);
					}
				}
			};
			ResponseEntity<StreamingResponseBody> entity = builder.body(body);
			// in.close();
			return entity;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	@DeleteMapping("delete/{id}")
	@ResponseBody
	public Result delect(@PathVariable String id) {
		Result result = fileService.delect(id);
		return result;
	}
}
