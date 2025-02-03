package com.springcrud.CurdOparation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springcrud.CurdOparation.model.ChartReportTbl;

@Repository
public interface ChartRepository extends JpaRepository<ChartReportTbl, Integer> {

	@Query("select tbl from ChartReportTbl tbl  where  tbl.type  like %?1% ")
	List<ChartReportTbl> findByType(String type);
	
}
