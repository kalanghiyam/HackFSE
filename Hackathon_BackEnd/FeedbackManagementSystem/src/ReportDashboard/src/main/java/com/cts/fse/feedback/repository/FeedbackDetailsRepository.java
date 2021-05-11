package com.cts.fse.feedback.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cts.fse.feedback.bean.FeedbackDetails;

@Repository
public interface FeedbackDetailsRepository extends CrudRepository<FeedbackDetails, Integer> {
	
	@Query("select feed from FeedbackDetails feed where feed.status= :eventStatus order by feed.inputType")
	Iterable<FeedbackDetails> getFeedbackList(@Param("eventStatus") String eventStatus);
}
