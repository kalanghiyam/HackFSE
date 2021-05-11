package com.cts.fse.feedback.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cts.fse.feedback.bean.EventEmployeeInfo;
import com.cts.fse.feedback.dto.DashboardReportDTO;
import com.cts.fse.feedback.dto.EventEmployeeInfoDTO;
import com.cts.fse.feedback.repository.EventEmployeeInfoRepository;
import com.cts.fse.feedback.utils.FeedbackConstants;




@Service
@Transactional
public class EventFileUploadServiceImpl implements EventFileUploadService {

	@Autowired
	private EventEmployeeInfoRepository  eventEmployeeInfoRepository;

	@Override
	public List<EventEmployeeInfo> getEventEmpList(int associateId) {
		return eventEmployeeInfoRepository.getEventEmpList(associateId);
	}

	@Override
	public List<EventEmployeeInfoDTO> getSendMailEventList() {
		 List<EventEmployeeInfoDTO> eventEmployeeInfoDTOList=new ArrayList<EventEmployeeInfoDTO>();
		 List<EventEmployeeInfo> eventEmployeeInfoList= eventEmployeeInfoRepository.getSendMailEventList(FeedbackConstants.FEEDBACK_NO);
		 if(!eventEmployeeInfoList.isEmpty()) {
			 EventEmployeeInfoDTO eventEmployeeInfoDTO=null;
			 for(EventEmployeeInfo eventEmployeeInfo: eventEmployeeInfoList) {
				  eventEmployeeInfoDTO=new EventEmployeeInfoDTO();
				  eventEmployeeInfoDTO.setAssociateId(eventEmployeeInfo.getEventEmployeeIdentity().getAssociateId());
				  eventEmployeeInfoDTO.setEventId(eventEmployeeInfo.getEventEmployeeIdentity().getEventId());
				  eventEmployeeInfoDTO.setAssociateName(eventEmployeeInfo.getAssociateName());
				  eventEmployeeInfoDTO.setBu(eventEmployeeInfo.getBu());
				  eventEmployeeInfoDTO.setEventStatus(eventEmployeeInfo.getEventStatus());
				  eventEmployeeInfoDTO.setResponded(eventEmployeeInfo.getResponded());
				  eventEmployeeInfoDTOList.add(eventEmployeeInfoDTO);
			 }
			
		 }
		return eventEmployeeInfoDTOList;
	}

	@Override
	public List<String> getEventDropDownList() {
		return eventEmployeeInfoRepository.getEventDropDownList(FeedbackConstants.FEEDBACK_NO);
	}
	
	@Override
	public List<EventEmployeeInfoDTO> getSearchList(String eventId) {
		List<EventEmployeeInfoDTO> eventEmployeeInfoDTOList=new ArrayList<EventEmployeeInfoDTO>();
		 List<EventEmployeeInfo> eventEmployeeInfoList=eventEmployeeInfoRepository.getSearchList(FeedbackConstants.FEEDBACK_NO, eventId);
		 if(!eventEmployeeInfoList.isEmpty()) {
			 EventEmployeeInfoDTO eventEmployeeInfoDTO=null;
			 for(EventEmployeeInfo eventEmployeeInfo: eventEmployeeInfoList) {
				  eventEmployeeInfoDTO=new EventEmployeeInfoDTO();
				  eventEmployeeInfoDTO.setAssociateId(eventEmployeeInfo.getEventEmployeeIdentity().getAssociateId());
				  eventEmployeeInfoDTO.setEventId(eventEmployeeInfo.getEventEmployeeIdentity().getEventId());
				  eventEmployeeInfoDTO.setAssociateName(eventEmployeeInfo.getAssociateName());
				  eventEmployeeInfoDTO.setBu(eventEmployeeInfo.getBu());
				  eventEmployeeInfoDTO.setEventStatus(eventEmployeeInfo.getEventStatus());
				  eventEmployeeInfoDTO.setResponded(eventEmployeeInfo.getResponded());
				  eventEmployeeInfoDTOList.add(eventEmployeeInfoDTO);
			 }
			
		 }
		return eventEmployeeInfoDTOList;
	}

	@Override
	public List<DashboardReportDTO> getEventDashboardReport(String role,String associateId) {
		List<DashboardReportDTO> returnList=new ArrayList<DashboardReportDTO>();
		Map<String,DashboardReportDTO> eventMap=new HashMap<String,DashboardReportDTO>();
		List<Object[]> objects=null;
		if(FeedbackConstants.EVENT_POC.equals(role)) {
			 objects=eventEmployeeInfoRepository.getEventDashboardReportForPoc(associateId);
		}else {
		  objects=eventEmployeeInfoRepository.getEventDashboardReport();
		}
		if(!objects.isEmpty()) {
			DashboardReportDTO dashboardReportDTO=null;
			for(Object[] obj:objects) {
				
				String key=String.valueOf(obj[0]);
				String eventStatus=String.valueOf(obj[1]);
				int count=Integer.parseInt(String.valueOf(obj[2]));
				if(eventMap.containsKey(key)) {
					dashboardReportDTO = eventMap.get(key);
				}else {
					dashboardReportDTO = new DashboardReportDTO();
					dashboardReportDTO.setEventId(String.valueOf(obj[0]));
				}
				if(eventStatus.equals(FeedbackConstants.PARTICIPATED)) {
					dashboardReportDTO.setParticipatedCount(count);
				}else if(eventStatus.equals(FeedbackConstants.NOTATTENDED)) {
					dashboardReportDTO.setNotAttendedCount(count);
				}else {
					dashboardReportDTO.setUnRegisteredCount(count);
				}
				eventMap.put(key, dashboardReportDTO);
			}
			
			
			
			for(String key : eventMap.keySet()) {
				returnList.add(eventMap.get(key));
			}
			if(returnList.size()>8) {
				returnList = returnList.subList(0,7);
			}
		}
		System.out.println(returnList);
		return returnList;
	}
	
	

}
