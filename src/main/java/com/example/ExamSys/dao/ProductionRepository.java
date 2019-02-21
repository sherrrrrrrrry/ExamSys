package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.Production;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long>{
	
	@Query("select p.productionUrl from Production p where p.user.id=?1 and p.name=?2")
	String findOneByUserIdAndName(Long id, String name);
	
	@Modifying
	@Transactional
	@Query("delete from Production p where p.user.id = ?1 and p.name = ?2")
	Integer deleteByUserIdAndName(Long id, String name);
	
}
