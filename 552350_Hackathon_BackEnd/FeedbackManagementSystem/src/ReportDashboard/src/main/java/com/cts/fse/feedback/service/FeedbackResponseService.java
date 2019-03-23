package com.cts.fse.feedback.service;

import java.util.List;
import com.cts.fse.feedback.bean.DashboardReportDTO;
import com.cts.fse.feedback.bean.EventResponseDTO;
import com.cts.fse.feedback.bean.ResponseSummaryDTO;

public interface FeedbackResponseService {

  public void saveEventFeedBackResponse(List<EventResponseDTO> responseDetails,String eventId,int associateId);
  
  public List<DashboardReportDTO> getSmileyReport(String role,String associateId);
  
  public List<DashboardReportDTO> getFeedbackRespondedList();
	
  public List<ResponseSummaryDTO> getResponseSummary(); 
}
