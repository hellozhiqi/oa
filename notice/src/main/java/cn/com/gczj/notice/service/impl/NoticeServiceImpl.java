package cn.com.gczj.notice.service.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.com.gczj.identity.domain.User;
import cn.com.gczj.identity.util.UserHoder;
import cn.com.gczj.notice.domain.Notice;
import cn.com.gczj.notice.domain.ReadingRecords;
import cn.com.gczj.notice.domain.Notice.Status;
import cn.com.gczj.notice.repository.NoticeRespsitory;
import cn.com.gczj.notice.repository.RecordsRepository;
import cn.com.gczj.notice.service.NoticeService;
import cn.com.gczj.vo.Result;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRespsitory noticeRespsitory;
	@Autowired
	private RecordsRepository recordsRepository;

	/**
	 * 编辑直接发布
	 */
	@Override
	public void publish(Notice notice) {

		if (StringUtils.isEmpty(notice.getId())) {
			notice.setId(null);
		}
		// 填充字段
		fillFiled(notice, Status.RELEASE);// 已发布
		// 保存数据
		this.noticeRespsitory.save(notice);
		// 建立索引表
		addIndex(notice.getId(),notice.getTitle());
	}

	/**
	 * 存放草稿
	 */
	@Override
	public void toDraft(Notice notice) {

		if (StringUtils.isEmpty(notice.getId())) {
			notice.setId(null);
		}
		// 填充字段
		fillFiled(notice, Status.DRAFT);// 存草稿
		// 保存数据
		this.noticeRespsitory.save(notice);
		// 建立索引表
		addIndex(notice.getId(),notice.getTitle());
	}

	/**
	 * 填充字段
	 * 
	 * @param notice 公告
	 * @param status 状态
	 */
	public void fillFiled(Notice notice, Status status) {

		notice.setReleaseTime(new Date());
		notice.setWriteTime(new Date());
		notice.setAuthor(UserHoder.get());
		notice.setStatus(status);// 状态
	}

	/**
	 * 查找公告
	 */
	@Override
	public Page<ReadingRecords> findNotice(Integer number, String keyword) {

		if (StringUtils.isEmpty(keyword)) {
			keyword = null;
		}
		Pageable pageable = PageRequest.of(number, 10);
		User author = UserHoder.get();
		Page<ReadingRecords> page = null;
		if (keyword == null) {
			// 根据用户关联阅读记录表,根据用户查询公告状态
			Page<ReadingRecords> dataPage = this.noticeRespsitory.findNotice(author, author, pageable);
			List<ReadingRecords> content = dataPage.getContent();
			page = new PageImpl<>(content, pageable, dataPage.getTotalElements());
		} else {
			keyword = "%" + keyword + "%";
			// 显示高亮
			List<Notice> queryReult = queryIndex(keyword);
			// 根据用户关联阅读记录表,根据用户查询公告状态
			Page<ReadingRecords> dataPage = this.noticeRespsitory.findNoticeKeyword(author, author, keyword, pageable);
			List<ReadingRecords> content = dataPage.getContent();
			page = new PageImpl<>(content, pageable, dataPage.getTotalElements());
			List<ReadingRecords> rr = page.getContent();
			if (queryReult != null) {
				for (Notice n : queryReult) {
					for (ReadingRecords readingRecord : rr) {
						Notice notice = readingRecord.getNotice();
						if(n.getId().equals(notice.getId())) {
							notice.setTitle(n.getTitle());
						}
					}
				}
			}
		}
		return page;
	}

	/**
	 * 删除公告
	 */
	@Override
	public Result delete(String id) {

		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		if (notice != null) {
			this.noticeRespsitory.delete(notice);
		}
		return Result.of(Result.STATUS_OK);
	}

	/**
	 * 编辑公告
	 * 
	 * @return
	 */
	@Override
	public Notice editNotice(String id) {
		return this.noticeRespsitory.findById(id).orElse(null);
	}

	/**
	 * [列表] 发布公告
	 */
	@Override
	@Transactional
	public void publishNotice(String id) {

		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		if (notice != null) {
			notice.setStatus(Status.RELEASE);
			notice.setReleaseTime(new Date());
		}
	}

	/**
	 * 撤回公告
	 */
	@Override
	@Transactional
	public void recallNotice(String id) {

		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		if (notice != null) {
			notice.setStatus(Status.RECALL);
		}
	}

	/**
	 * 已阅读公告
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public Result readedNotice(String id) {

		User user = UserHoder.get();
		Notice notice = this.noticeRespsitory.findById(id).orElse(null);
		ReadingRecords oa_records = this.recordsRepository.findByNoticeAndUser(notice, user);
		if (oa_records == null) {

			ReadingRecords records = new ReadingRecords();
			records.setNotice(notice);
			records.setReadTime(new Date());
			records.setUser(user);
			// 保存数据
			this.recordsRepository.save(records);

			return Result.of(Result.STATUS_OK);
		}
		return Result.of(Result.STATUS_ERROR);

	}

	/**
	 * 查看公告
	 */
	@Override
	public Notice lookNotice(String id) {
		return this.noticeRespsitory.findById(id).orElse(null);
	}

	// 添加索引到索引库
	private static void addIndex(String id,String title) {
		try {
			// 指定索引的目录位置
			Directory directory = FSDirectory.open(Paths.get("/home/mia/lucene_db/oa_tb"));
			// 创建分词器
			Analyzer analyzer = new IKAnalyzer();
			// IndexWriterConfig ，可以指定添加索引配置信息
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			// 指定索引追加模式
			config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			// 通过indexWriter，可以对索引进行添加、删除、修改
			IndexWriter indexWriter = new IndexWriter(directory, config);

			// 在Lucene中，一个Document就是一条记录
			Document doc = new Document();
			// StringField 不会对关键字分词
			doc.add(new StringField("id", id, Store.YES));
			// TextField 会对关键字分词,具体需要看分词器的选择
			doc.add(new TextField("title", title, Store.YES));

			// 将信息写入索引库(添加一条记录)
			indexWriter.addDocument(doc);
			indexWriter.commit();
			indexWriter.close();
			System.out.println("--------------------添加索引成功！---------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<Notice> queryIndex(String keyword) {

		try {
			// 指定索引的目录位置
			Directory directory = FSDirectory.open(Paths.get("/home/mia/lucene_db/oa_tb"));
			// 通过DirectoryReader来加载索引库
			DirectoryReader directoryReader = DirectoryReader.open(directory);
			// 创建分词器
			Analyzer analyzer = new IKAnalyzer();
			// 创建QueryParser对象， 需要查询的字段以及分词器的信息
			QueryParser queryParser = new MultiFieldQueryParser(new String[] { "title", "content" }, analyzer);

			// 获取Query对象，并封装查询信息
			Query query = queryParser.parse(keyword);

			// 创建IndexSearcher对象，将已加载好的索引信息传入
			IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
			// 开始索引检索，最多限制返回最优4条
			TopDocs topDocs = indexSearcher.search(query, 4);
			/***************************************
			 * 高亮显示
			 ******************************************/
			// 创建格式器,通过格式器指定高亮效果
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			// 将关键字进行传入,返回评分对象
			Scorer scorer = new QueryScorer(query);
			// 截取片段(高亮部分),最多显示20个字符
			Fragmenter fragmenter = new SimpleFragmenter();
			// 创建高亮对象
			Highlighter highlighter = new Highlighter(formatter, scorer);
			// 设置高亮显示截取部分
			highlighter.setTextFragmenter(fragmenter);
			/***************************************
			 * 高亮显示
			 ******************************************/
			long totalHits = topDocs.totalHits;
			System.out.println("满足条件的记录数=" + totalHits);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			List<Notice> list = new LinkedList<>();
			for (ScoreDoc scoreDoc : scoreDocs) {
				System.out.println("文档得分：" + scoreDoc.score);
				int indexId = scoreDoc.doc;
				System.out.println("文档id：" + indexId);

				Document doc = indexSearcher.doc(indexId);
				String id = doc.get("id");
				String title = doc.get("title");
				String postTitle = highlighter.getBestFragment(analyzer, "title", title);

				System.out.println("高亮显示前title=" + title);
				System.out.println("高亮显示后title=" + postTitle);
				Notice notice = new Notice();
				notice.setId(id);
				notice.setTitle(postTitle == null ? title : postTitle);
				list.add(notice);
			}
			System.out.println("--------------------全文检索完毕！---------------------");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
