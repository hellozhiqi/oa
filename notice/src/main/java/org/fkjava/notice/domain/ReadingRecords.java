package org.fkjava.notice.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.identity.domain.User;
import org.hibernate.annotations.GenericGenerator;

/**
 * 用户查阅公告的 阅读记录
 *
 */
@Entity
@Table(name = "oa_readed_record")
public class ReadingRecords {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	// 多条公告可被一个用户查阅
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;// 记录该用户的对公告查阅记录情况

	@Temporal(TemporalType.TIMESTAMP)
	private Date readTime;// 阅读时间

	@ManyToOne
	@JoinColumn(name = "notice_id")
	private Notice notice;// 阅读那条公告

	public ReadingRecords() {
		super();
	}

	public ReadingRecords(String id, Date readTime, Notice notice) {
		super();
		this.id = id;
		this.readTime = readTime;
		this.notice = notice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

}
