package cn.com.gczj.hr.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.com.gczj.hr.domain.Employee;
import cn.com.gczj.identity.domain.User;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{

	Employee findByUser(User user);

}
