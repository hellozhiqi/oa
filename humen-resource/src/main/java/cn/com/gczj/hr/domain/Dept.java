package cn.com.gczj.hr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "oa_dept")
public class Dept {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonIgnore
	private Dept parent;// 上级部门
	private String name;// 部门名称

	@OneToOne
	@JoinColumn(name="manager_id")
	private Employee manager;// 经理

	@OneToMany(mappedBy = "dept")
	@JsonIgnore
	private List<Employee> employees;// 部门下的所有员工

	@OneToMany(mappedBy = "parent")
	@OrderBy("number") // 下级菜单查询时候,使用number排序
	@JsonProperty("children") // 生成json数据时,使用别名
	private List<Dept> childsDept;// 下级部门
	private Double number;// 部门排序使用
	private String telephone;// 部门电话
	private String fax;// 部门传真
	private String fun;// 部门职能

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Dept> getChildsDept() {
		return childsDept;
	}

	public void setChildsDept(List<Dept> childsDept) {
		this.childsDept = childsDept;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFun() {
		return fun;
	}

	public void setFun(String fun) {
		this.fun = fun;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", parent=" + parent + ", name=" + name + ", manager=" + manager + ", employees="
				+ employees + ", childsDept=" + childsDept + ", number=" + number + ", telephone=" + telephone
				+ ", fax=" + fax + ", fun=" + fun + "]";
	}

}
