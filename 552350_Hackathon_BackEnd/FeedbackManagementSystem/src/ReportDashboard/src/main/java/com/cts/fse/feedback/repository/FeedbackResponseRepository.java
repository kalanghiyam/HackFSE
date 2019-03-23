package com.cts.fse.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.fse.feedback.bean.FeedbackResponse;

@Repository
public interface FeedbackResponseRepository extends CrudRepository<FeedbackResponse, Integer> {

	
	
	@Query("select response.eventFeedbackResponseIdentity.eventId as eventId,avg(response.smileyCount) as "
			+ "averageSmileyCount from FeedbackResponse response inner join  response.feedbackDetails details"
			+ " where details.inputType='Smiley' group by response.eventFeedbackResponseIdentity.eventId order by response.eventEmployeeInfo.eventDate desc")
	List<Object[]> getSmileyReport();
	
	@Query("select response.eventFeedbackResponseIdentity.eventId as eventId,avg(response.smileyCount) as "
			+ "averageSmileyCount from FeedbackResponse response inner join  response.feedbackDetails details"
			+ " where details.inputType='Smiley' and response.eventEmployeeInfo.eventSummaryDetails.pocId=:pocId  group by response.eventFeedbackResponseIdentity.eventId "
			+ " order by response.eventEmployeeInfo.eventDate desc")
	List<Object[]> getSmileyReportForPoc(@Param("pocId") String pocId);
	
}
