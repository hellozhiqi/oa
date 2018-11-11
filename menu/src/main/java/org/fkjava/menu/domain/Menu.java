package org.fkjava.menu.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.fkjava.identity.domain.Role;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="oa_menu")
public class Menu {

	@Id
	@GeneratedValue(generator="uuid2")
	@GenericGenerator(name="uuid2",strategy="uuid2")
	private String id;
	//菜单名称
	private String name;
	//菜单的url
	private String url;
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToMany
	@JoinTable(name="menu_roles")
	@OrderBy("roleName")
	private List<Role> roles;
	
	private Double number;//排序的序号
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	@JsonIgnore
	private Menu parent;//上级菜单的id
	
	@OneToMany(mappedBy="parent")
	@OrderBy("number")//下级菜单查询时候,使用number排序
	@JsonProperty("children")//生成json数据时,使用别名
	private List<Menu> childs;//字节点
	
	public static enum Type{
		/**
		 * 连接类型
		 */
		LINK,
		/**
		 * 按钮类型
		 */
		BUTTON
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChilds() {
		return childs;
	}

	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", url=" + url + ", type=" + type + ", roles=" + roles
				+ ", number=" + number + ", parent=" + parent + ", childs=" + childs + "]";
	}
	
}
