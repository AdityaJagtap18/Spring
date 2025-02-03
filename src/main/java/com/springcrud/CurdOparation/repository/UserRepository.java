package com.springcrud.CurdOparation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springcrud.CurdOparation.model.UserTbl;

@Repository
public interface UserRepository extends JpaRepository<UserTbl, Integer> {
	
	@Query("select tbl from UserTbl tbl  where  tbl.username = ?1")
	UserTbl findByUsername(String username);
	
	@Query("select tbl from UserTbl tbl  where  tbl.role like  %?1%")
	List<UserTbl> findAllByUserRole(String userRole);

	@Query("select tbl from UserTbl tbl  where  tbl.username like %?1%")
	List<UserTbl> searchByUsername(Optional<String> search);

}
