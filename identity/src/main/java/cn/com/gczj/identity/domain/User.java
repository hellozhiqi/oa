package cn.com.gczj.identity.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "oa_user")
public class User{
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	private String name;
	@Column(unique = true, length = 25)
	private String loginName;
	private String password;
	
	@JoinTable(name="oa_user_roles",
			joinColumns= {@JoinColumn(name="user_id")},
			inverseJoinColumns= {@JoinColumn(name="role_id")})
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Role> roles;
	/**
	 *  用于封装不固定角色,不持久化到数据库
	 */
	@Transient
	private List<Role> unFixedRole;
	/**
	 * 	过期时间,当前时间+2个月(每次修改)
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;
	
	//把枚举变量名称(STRING)存储到数据库
	// 默认存储索引(ORDINAL)
	@Enumerated(EnumType.STRING)
	private Status status;
	/**内部枚举 列举用户状态*/
	public static enum Status{
		/**
		 * 正常
		 */
		NORMAL,
		/**
		 * 过期
		 */
		EXPIRED,
		/**
		 * 禁用
		 */
		DISABLE
	}
	
	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public Status getStatus() {
		
		if(this.status==Status.DISABLE) {
			return Status.DISABLE;
		}
		if(this.expiredTime!=null && System.currentTimeMillis()>=this.expiredTime.getTime()) {
			return Status.EXPIRED;
		}
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getUnFixedRole() {
		return unFixedRole;
	}

	public void setUnFixedRole(List<Role> unFixedRole) {
		this.unFixedRole = unFixedRole;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", loginName=" + loginName + ", password=" + password + "]";
	}

}
