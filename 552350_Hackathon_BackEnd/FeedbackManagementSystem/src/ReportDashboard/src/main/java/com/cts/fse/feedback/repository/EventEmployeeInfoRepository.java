package com.cts.fse.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.fse.feedback.bean.EventEmployeeIdentity;
import com.cts.fse.feedback.bean.EventEmployeeInfo;

@Repository
public interface EventEmployeeInfoRepository extends JpaRepository<EventEmployeeInfo, EventEmployeeIdentity>{
	
	@Query("select emp from EventEmployeeInfo emp where emp.eventEmployeeIdentity.associateId= :associateId"
			+ " and emp.emailSendDate=(select max(emp.emailSendDate) from EventEmployeeInfo  emp where "
			+ "emp.eventEmployeeIdentity.associateId= :associateId )")
	List<EventEmployeeInfo> getEventEmpList(@Param("associateId") int associateId);

	
	@Query("select emp from EventEmployeeInfo emp where emp.emailCount<= :emailCount "
			+ "and emp.responded= :responded and emp.eventStatus= :eventStatus")
	List<EventEmployeeInfo> getEventEmpEmailList(@Param("emailCount") int emailCount,@Param("responded") String responded,
			@Param("eventStatus") String eventStatus);
	
	@Query("select emp from EventEmployeeInfo emp where emp.responded= :responded")
	List<EventEmployeeInfo> getSendMailEventList(@Param("responded") String responded);
	
	@Query("select distinct emp.eventEmployeeIdentity.eventId from EventEmployeeInfo emp where emp.responded= :responded")
	List<String> getEventDropDownList(@Param("responded") String responded);
	
	@Query("select emp from EventEmployeeInfo emp where "
			+ "emp.eventEmployeeIdentity.associateId= :associateId and emp.eventEmployeeIdentity.eventId= :eventId ")
	List<EventEmployeeInfo> findByEventId(@Param("associateId") int associateId,@Param("eventId") String eventId);
	
	@Query("select emp from EventEmployeeInfo emp where emp.responded= :responded and emp.eventEmployeeIdentity.eventId= :eventId")
	List<EventEmployeeInfo> getSearchList(@Param("responded") String responded,@Param("eventId") String eventId);

	@Query("select info.eventEmployeeIdentity.eventId,info.eventStatus,count(info.eventStatus) from EventEmployeeInfo info inner join  info.eventSummaryDetails details"
			+ " where details.month= :currentMonth group by info.eventEmployeeIdentity.eventId,info.eventStatus")
	List<Object[]> getEventDashboardReport(@Param("currentMonth") String currentMonth);
	

	@Query("select info.eventEmployeeIdentity.eventId,count(info.responded) from EventEmployeeInfo info where info.responded='Y'  group by info.eventEmployeeIdentity.eventId")
	List<Object[]> getFeedbackRespondedList();
}
