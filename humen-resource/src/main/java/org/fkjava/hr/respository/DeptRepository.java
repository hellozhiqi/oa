package org.fkjava.hr.respository;

import java.util.List;

import org.fkjava.hr.domain.Dept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Dept, String> {

	Dept findByNameAndParent(String name, Dept parent);

	Dept findByNameAndParentNull(String name);

	@Query("select max(number) from Dept where parent is null")
	Double findMaxNumberByParentNull();

	@Query("select max(number) from Dept where parent=:parent")
	Double findMaxNumberByParent(@Param("parent") Dept parent);

	List<Dept> findByParentNullOrderByNumber();

	Page<Dept> findByParentAndNumberLessThanOrderByNumberDesc(Dept parent, Double number, Pageable pageable);

	Page<Dept> findByParentAndNumberGreaterThanOrderByNumberAsc(Dept parent, Double number, Pageable pageable);

}
