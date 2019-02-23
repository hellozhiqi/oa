package cn.com.gczj.identity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.com.gczj.identity.domain.User;

@Repository
public interface UserRepository  extends JpaRepository<User, String> {

	User findByLoginName(String loginName);

	Page<User> findByNameContaining(String keyword, Pageable pageable);


}
