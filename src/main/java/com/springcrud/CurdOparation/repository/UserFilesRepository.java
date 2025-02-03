package com.springcrud.CurdOparation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springcrud.CurdOparation.model.UserFilesTbl;
import com.springcrud.CurdOparation.model.UserTbl;

public interface UserFilesRepository extends JpaRepository<UserFilesTbl, Integer>{
	
	@Query("select tbl from UserTbl tbl  where  tbl.fileId = ?1")
	Optional<UserFilesTbl> findByFileId(int fileId);
	
}
