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

import com.example.ExamSys.domain.Authority;
import com.example.ExamSys.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findOneByLogin(String login);
	
	Optional<User> findOneByEmail(String email);
	
	Optional<User> findOneByPhoneNumber(String phoneNumber);
	
	List<User> findAllByEnabledFalse();
	
	Optional<User> findOneById(Long id);
	
	@Modifying
	@Transactional
	@Query("delete from User u where u.login in (:logins)")
	void deleteByLogins(@Param("logins") List<String> logins);
	
	@Query("select u.authorities from User u where u.login = ?1")
	Set<Authority> findAuthoritiesByLogin(String login);
	
	@Query("select u.id from User u where u.login = ?1")
	Long findIdByLogin(String login);
	
	@Modifying
	@Transactional
	@Query("UPDATE User user set user.enabled=true where user.login in (:logins)")
	void setEnabledTrueByIdIn(@Param("logins") List<String> logins);
	
	@Modifying
	@Transactional
	@Query("UPDATE User user set user.password=?1 where user.login=?2")
	void updatePasswordByLogin(String password, String login);
}
