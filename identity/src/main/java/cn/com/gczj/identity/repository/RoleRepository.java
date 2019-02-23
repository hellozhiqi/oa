package cn.com.gczj.identity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.com.gczj.identity.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

	Optional<Role>  findByRoleKey(String roleKey);

	List<Role> findByFixedTrue();
	 
	List<Role> findByFixedFalse();


}
