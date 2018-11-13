package org.fkjava.identity.repository;

import java.util.Optional;

import org.fkjava.identity.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, String> {

	User findByLoginName(String loginName);

	Page<User> findByNameContaining(String keyword, Pageable pageable);


}
