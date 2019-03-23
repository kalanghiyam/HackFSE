package com.cts.fse.feedback.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.cts.fse.feedback.bean.DashboardReportDTO;
import com.cts.fse.feedback.bean.EventEmployeeInfo;
import com.cts.fse.feedback.bean.EventResponseDTO;
import com.cts.fse.feedback.bean.FeedbackResponse;
import com.cts.fse.feedback.bean.FeedbackResponseIdentity;
import com.cts.fse.feedback.bean.ResponseSummaryDTO;
import com.cts.fse.feedback.repository.EventEmployeeInfoRepository;
import com.cts.fse.feedback.repository.FeedbackResponseRepository;


@Service
@Transactional
public class FeedbackResponseServiceImpl implements FeedbackResponseService {

	@Autowired
	private FeedbackResponseRepository eventFeedbackResponseRepository;
	
	@Autowired
	private EventEmployeeInfoRepository eventEmployeeInfoRepository;
	
	
	@Override
	public void saveEventFeedBackResponse(List<EventResponseDTO> responseDetails,String eventId,int associateId) {
		List<FeedbackResponse> eventFeedbackResponseList=new ArrayList<FeedbackResponse>();
		FeedbackResponse eventFeedbackResponse=null;
		for(com.cts.fse.feedback.bean.EventResponseDTO eventResponseDTO : responseDetails) {
			eventFeedbackResponse = new FeedbackResponse();
			eventFeedbackResponse.setEventFeedbackResponseIdentity(new FeedbackResponseIdentity());
			eventFeedbackResponse.getEventFeedbackResponseIdentity().setAssociateId(associateId);
			eventFeedbackResponse.getEventFeedbackResponseIdentity().setEventId(eventId);
			eventFeedbackResponse.getEventFeedbackResponseIdentity().setFeedbackId(eventResponseDTO.getId());
			if(eventResponseDTO.getInputType().equals("Smiley")) {
				eventFeedbackResponse.setSmileyCount(Integer.parseInt(eventResponseDTO.getSmileyValue()));
				eventFeedbackResponseList.add(eventFeedbackResponse);
			}else if(eventResponseDTO.getInputType().equals("TextArea")) {
				eventFeedbackResponse.setFeedbackResponse(eventResponseDTO.getFeedbackResponse());
				eventFeedbackResponseList.add(eventFeedbackResponse);
			}else {
				if(eventResponseDTO.isSelected()) {
					eventFeedbackResponse.getEventFeedbackResponseIdentity().setFeedbackId(eventResponseDTO.getId());
					eventFeedbackResponseList.add(eventFeedbackResponse);
				}
				
			}
		}
	
		eventFeedbackResponseRepository.saveAll(eventFeedbackResponseList);
		List<EventEmployeeInfo> eventEmployeeInfoList = eventEmployeeInfoRepository.findByEventId(associateId,eventId);
		if(!CollectionUtils.isEmpty(eventEmployeeInfoList)) {
			EventEmployeeInfo eventEmployeeInfo = eventEmployeeInfoList.get(0);
			eventEmployeeInfo.setResponded("Y");
			eventFeedbackResponseRepository.saveAll(eventFeedbackResponseList);
			eventEmployeeInfoRepository.save(eventEmployeeInfo);
		}
		
	}


	@Override
	public List<DashboardReportDTO> getSmileyReport(String role,String associateId) {
		List<DashboardReportDTO> reportList= new ArrayList<DashboardReportDTO>();
		List<Object[]> objects=null;
		if(role.equalsIgnoreCase("Event POC")){
			objects=eventFeedbackResponseRepository.getSmileyReportForPoc(associateId);
		}else {
			objects=eventFeedbackResponseRepository.getSmileyReport();
		}
		if(!objects.isEmpty()) {
			DashboardReportDTO dashboardReportDTO=null;
			for(Object[] obj:objects) {
				dashboardReportDTO = new DashboardReportDTO();
				dashboardReportDTO.setEventId(String.valueOf(obj[0]));
				dashboardReportDTO.setAverageSmileyCount(new BigDecimal(String.valueOf(obj[1])));
				reportList.add(dashboardReportDTO);
			}
			if(reportList.size()>8) {
				reportList = reportList.subList(0,7);
			}
		}
		return reportList;
	}
	
	@Override
	public List<DashboardReportDTO> getFeedbackRespondedList() {
		List<DashboardReportDTO> reportList= new ArrayList<DashboardReportDTO>();
		List<Object[]> objects=eventEmployeeInfoRepository.getFeedbackRespondedList();
		if(!objects.isEmpty()) {
			DashboardReportDTO dashboardReportDTO=null;
			for(Object[] obj:objects) {
				dashboardReportDTO = new DashboardReportDTO();
				dashboardReportDTO.setEventId(String.valueOf(obj[0]));
				dashboardReportDTO.setAverageSmileyCount(new BigDecimal(String.valueOf(obj[1])));
				reportList.add(dashboardReportDTO);
			}
			if(reportList.size()>8) {
				reportList = reportList.subList(0,7);
			}
		}
		return reportList;
	}

	@Override
	public List<ResponseSummaryDTO> getResponseSummary(){
		List<ResponseSummaryDTO> response = new ArrayList<>();
		for(FeedbackResponse feedback : eventFeedbackResponseRepository.findAll()) {
			String event=feedback.getEventFeedbackResponseIdentity().getEventId();
			String feedRes=feedback.getFeedbackResponse();
			String status=feedback.getFeedbackDetails().getStatus();
			ResponseSummaryDTO responseObj = new ResponseSummaryDTO();
			responseObj.setEvent(event);
			responseObj.setFeedbackResponse(feedRes);
			responseObj.setStatus(status);
			response.add(responseObj);
		}
		return response; 
	}

}
