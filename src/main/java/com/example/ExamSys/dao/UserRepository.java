package com.example.ExamSys.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findOneByLogin(String login);
	
	Optional<User> findOneByEmail(String email);
	
	Set<User> findAllByEnabledFalse();
	
	@Modifying
	@Transactional
	@Query("UPDATE User user set user.enabled=true where user.id in (:ids)")
	void setEnabledTrueByIdIn(@Param("ids") List<Long> ids);
}
