package org.fkjava.hr.service;

import java.util.List;

import org.fkjava.hr.domain.Dept;
import org.fkjava.vo.Result;

public interface DeptService {

	/**
	 * 保存部门
	 * 
	 * @param dept
	 */
	void save(Dept dept);

	/**
	 * 查找一级部门
	 * @return
	 */
	List<Dept> findTopDept();

	/**
	 * 拖拽菜单
	 * @param id
	 * @param targetId
	 * @param moveType
	 * @return Result
	 */
	Result move(String id, String targetId, String moveType);

	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	Result delete(String id);

}
