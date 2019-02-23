package cn.com.gczj.notice.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import cn.com.gczj.identity.domain.User;

@Entity
@Table(name = "oa_notice")
public class Notice {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	private String title;// 标题

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private NoticeType type;// 类型

	@Lob
	private String content;// 正文
	@Temporal(TemporalType.TIMESTAMP)
	private Date writeTime;// 撰写时间

	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseTime;// 发布时间

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;// 作者

	// 一条公告可被多个用户去查阅
	@OneToMany(mappedBy = "notice")
	private List<ReadingRecords> readingRecords;

	// 状态:草稿,已读,未读
	@Enumerated(EnumType.STRING)
	private Status status;// 公告状态

	public static enum Status {

		DRAFT, // 草稿
		RELEASE, // 已发布
		RECALL,// 撤回
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public NoticeType getType() {
		return type;
	}

	public void setType(NoticeType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<ReadingRecords> getReadingRecords() {
		return readingRecords;
	}

	public void setReadingRecords(List<ReadingRecords> readingRecords) {
		this.readingRecords = readingRecords;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
