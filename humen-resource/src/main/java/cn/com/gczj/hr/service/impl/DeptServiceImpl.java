package cn.com.gczj.hr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.gczj.hr.domain.Dept;
import cn.com.gczj.hr.domain.Employee;
import cn.com.gczj.hr.respository.DeptRepository;
import cn.com.gczj.hr.respository.EmployeeRepository;
import cn.com.gczj.hr.service.DeptService;
import cn.com.gczj.vo.Result;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptRepository deptRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 保存部门
	 */
	@Override
	public void save(Dept dept) {

		if (StringUtils.isEmpty(dept.getId())) {
			dept.setId(null);
		}
		if (dept.getParent() != null && StringUtils.isEmpty(dept.getParent().getId())) {
			dept.setParent(null);// 保存的节点属于上级部门
		}
		// 1.是否有上级部门
		Dept oa_dept = null;
		if (dept.getParent() != null) {
			// 新增时,有上级部门
			oa_dept = this.deptRepository.findByNameAndParent(dept.getName(), dept.getParent());
		} else {// 没有上级部门
			oa_dept = this.deptRepository.findByNameAndParentNull(dept.getName());
		}

		// 2.设置当前经理的为员工
		Employee employee = dept.getManager();
		if (dept.getManager() != null //
				&& dept.getManager().getUser() != null //
				&& !StringUtils.isEmpty(dept.getManager().getUser().getId())) {

			Employee manager = this.employeeRepository.findByUser(employee.getUser());
			if (manager == null) {// 该经理不是员工行列
				// 新增一个员工
				this.employeeRepository.save(employee);
			} else {// 原本就是部门的员工
				employee = manager;
			}
			employee.setDept(dept);// 为经理设置部门
			dept.setManager(employee);// 将该员工设置为经理
		} else {
			// 没有选择部门经理
			// 在后面的其他业务处理的过程，如果一定需要部门经理处理，那么就要求使用独立的一个表来记录【委托】，把部门经理的职责委托给其他的用户
			// 委托的用户，不一定是当前部门的
			// 现在这里不处理委托
			dept.setManager(null);
		}
		// 3. 设置排列序号(菜单可以拖拽)
		// 找到同级最大的number，然后加10000000，就形成一个新的number作为当前菜单的number
		if (oa_dept != null) {
			dept.setNumber(oa_dept.getNumber());
		} else {
			Double maxNumber;
			if (dept.getParent() == null) {
				maxNumber = deptRepository.findMaxNumberByParentNull();
			} else {
				maxNumber = deptRepository.findMaxNumberByParent(dept.getParent());
			}
			if (maxNumber == null) {
				maxNumber = 0.0;
			}
			Double number = maxNumber + 100000.0;
			dept.setNumber(number);
		}
		// 保存数据
		this.deptRepository.save(dept);
	}

	/**
	 * 查找一级部门
	 */
	@Override
	public List<Dept> findTopDept() {
		return this.deptRepository.findByParentNullOrderByNumber();
	}

	/**
	 * 拖拽菜单
	 */
	@Override
	@Transactional
	public Result move(String id, String targetId, String moveType) {

		if (StringUtils.isEmpty(id)) {
			return Result.of(Result.STATUS_ERROR);
		}
		// 移动的部门
		Dept dept = this.deptRepository.findById(id).orElse(null);
		// 情况1：拖拽节点到最后/最前，没有目标的情况下
		if (StringUtils.isEmpty(targetId)) {

			Double maxNumber = this.deptRepository.findMaxNumberByParentNull();
			if (maxNumber == null) {
				maxNumber = 0.0;
			}
			// 重新设置number，以及parent
			copy(maxNumber, dept, null);

			return Result.of(Result.STATUS_OK);
		}
		Dept targetDept = this.deptRepository.findById(targetId).orElse(null);
		// 情况2：拖拽节点到目标的里面 “inner”
		if ("inner".equals(moveType)) {

			// 同一部门有相同的子部门
			for (Dept childs : targetDept.getChildsDept()) {
				if (childs.getName().equals(dept.getName())) {
					return Result.of(Result.STATUS_ERROR);
				}
			}
			Double maxNumber = this.deptRepository.findMaxNumberByParent(targetDept);
			if (maxNumber == null) {
				maxNumber = 0.0;
			}
			maxNumber += 100000.0;
			// 重新设置number，以及parent
			copy(maxNumber, dept, targetDept);

		} else if ("prev".equals(moveType)) { // 情况3：拖拽节点到目标的前面 “prev”
			Pageable pageable = PageRequest.of(0, 1);
			Page<Dept> prevs = this.deptRepository.findByParentAndNumberLessThanOrderByNumberDesc(
					targetDept.getParent(), targetDept.getNumber(), pageable);

			Double targetNumber = targetDept.getNumber();
			Double number;// 移动节点新的number
			if (prevs.getNumberOfElements() > 0) {// 表示：目标前面有前一个子节点
				// 目标前一个的子节点的number
				Double targetPrevNumber = prevs.getContent().get(0).getNumber();
				number = (targetPrevNumber + targetNumber) / 2;
			} else {// 目标前面没有前一个子节点
				number = targetNumber / 2;
			}
			// 重新设置number，以及parent
			copy(number, dept, targetDept.getParent());

		} else if ("next".equals(moveType)) {// 情况4：拖拽节点到目标的后面 “next”

			Pageable pageable = PageRequest.of(0, 1);

			Page<Dept> nexts = this.deptRepository.findByParentAndNumberGreaterThanOrderByNumberAsc(
					targetDept.getParent(), targetDept.getNumber(), pageable);
			Double targetNumber = targetDept.getNumber();
			Double number;
			if (nexts.getNumberOfElements() > 0) {// 表示，目标有下一个子节点
				Double targetNextNumber = nexts.getContent().get(0).getNumber();
				number = (targetNextNumber + targetNumber) / 2;
			} else {
				number = targetNumber + 100000.0;
			}
			// 重新设置number，以及parent
			copy(number, dept, targetDept.getParent());

		} else {
			throw new IllegalArgumentException("非法的菜单移动类型，只允许inner、prev、next三选一。");
		}
		return Result.of(Result.STATUS_OK);
	}

	/**
	 * 相同代码
	 * 
	 * @param maxNumber
	 * @param dept      移动部门
	 * @param parent    上级部门
	 */
	private void copy(Double maxNumber, Dept dept, Dept parent) {

		dept.setNumber(maxNumber);
		dept.setParent(parent);
	}

	@Override
	public Result delete(String id) {

		Dept dept = this.deptRepository.findById(id).orElse(null);
		if (dept != null) {
			if (dept.getChildsDept().isEmpty()) {
				this.deptRepository.delete(dept);
			} else {
				return Result.of(Result.STATUS_ERROR);
			}
		}
		return Result.of(Result.STATUS_OK);
	}
}
