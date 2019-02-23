package cn.com.gczj.menu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.com.gczj.identity.domain.Role;
import cn.com.gczj.menu.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

	Menu findByNameAndParent(String name, Menu parent);

	Menu findByNameAndParentNull(String name);

	@Query("select max(number) from Menu where parent is null")
	Double findMaxNumberByParentNull();

	@Query("select max(number) from Menu  where parent= :parent")
	Double findMaxNumberByParent(@Param("parent") Menu parent);

	List<Menu> findByParentNullOrderByNumber();

	Page<Menu> findByParentAndNumberLessThanOrderByNumberDesc(Menu parent, Double number, Pageable pageable);

	Page<Menu> findByParentAndNumberGreaterThanOrderByNumberAsc(Menu parent, Double number, Pageable pageable);

	List<Menu> findDistinctByRolesIn(List<Role> roles);

}
