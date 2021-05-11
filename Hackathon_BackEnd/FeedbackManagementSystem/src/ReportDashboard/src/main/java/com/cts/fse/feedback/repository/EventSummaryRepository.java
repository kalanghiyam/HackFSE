package com.cts.fse.feedback.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.fse.feedback.bean.EventSummaryDetails;

@Repository
public interface EventSummaryRepository extends CrudRepository<EventSummaryDetails, String> {

	@Query("select distinct baseLocation from EventSummaryDetails")
	Iterable<String> getLocations();
	
	@Query("select distinct project from EventSummaryDetails")
	Iterable<String> getProjects();
	
	@Query("select emp from EventSummaryDetails emp where baseLocation=:location and project=:proJect")
	Iterable<EventSummaryDetails> searchSummary(@Param("location") String location,
			@Param("proJect") String project);
	
	@Query("select emp from EventSummaryDetails emp where baseLocation=:location")
	Iterable<EventSummaryDetails> searchSummaryLocation(@Param("location") String location);
	
	@Query("select emp from EventSummaryDetails emp where project=:proJect") 
	Iterable<EventSummaryDetails> searchSummaryProject(@Param("proJect") String project);
	
	
}
    