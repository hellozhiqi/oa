package cn.com.gczj.hr.service;

import java.util.List;

import cn.com.gczj.hr.domain.Dept;
import cn.com.gczj.vo.Result;

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
