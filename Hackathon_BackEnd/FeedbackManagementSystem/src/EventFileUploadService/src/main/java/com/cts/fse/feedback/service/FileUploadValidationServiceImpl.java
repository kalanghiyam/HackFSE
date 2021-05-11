package com.cts.fse.feedback.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fse.feedback.bean.EventEmployeeInfo;
import com.cts.fse.feedback.bean.EventSummaryDetails;
import com.cts.fse.feedback.repository.EventEmployeeInfoRepository;
import com.cts.fse.feedback.repository.EventSummaryDetailsRepository;
import com.cts.fse.feedback.utils.FeedbackConstants;
import com.cts.fse.feedback.utils.Utils;

@Service
@Transactional
public class FileUploadValidationServiceImpl implements FileUploadValidationService {

	@Autowired
	private EventEmployeeInfoRepository eventEmployeeInfoRepository;
	
	@Autowired
	private EventSummaryDetailsRepository eventSummaryDetailsRepository;
	
	@Value("${file.path}")
	private String filePath;

	
	@Override
	public void executeInputEventFile() {
		 try {
		 Map<String,List<EventEmployeeInfo>> eventMap=new HashMap<String,List<EventEmployeeInfo>>();
		 File directory = new File (filePath);
		 if(directory.isDirectory()) {
			 for (File file : directory.listFiles()) {
				 String fileName=file.getName();
				 FileInputStream inputStream=new FileInputStream(file.getAbsolutePath());
				 List<EventEmployeeInfo> eventEmployeeInfoList=null;
				 if(fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
					 if(fileName.startsWith("Outreach Events Summary")) {
						 saveEventSummaryDetails(inputStream);
					  }else if(fileName.startsWith("OutReach Event Information")) {
						  eventEmployeeInfoList= Utils.parseExcelFileToBeans(inputStream,FeedbackConstants.EVENT_EMPINFO_MAPPER);
						  eventMap.put(FeedbackConstants.PARTICIPATED, eventEmployeeInfoList);
					  }else  if(fileName.startsWith("Volunteer_Enrollment Details_Not_Attend")) {
						  eventEmployeeInfoList=Utils.parseExcelFileToBeans(inputStream,FeedbackConstants.EVENT_EMP_LIST);
						  eventMap.put(FeedbackConstants.NOTATTENDED, eventEmployeeInfoList);
					  }else {
						  eventEmployeeInfoList=Utils.parseExcelFileToBeans(inputStream,FeedbackConstants.EVENT_EMP_LIST);
						  eventMap.put(FeedbackConstants.UNREGISTERED, eventEmployeeInfoList);
					  }
				 }
				 if(inputStream!=null) {
					 inputStream.close();
				 }
			 }
			 if(!eventMap.isEmpty()) {
				  for(String eventStatus : eventMap.keySet()) {
					validateEventEmpList(eventMap.get(eventStatus), eventStatus);
				  }
			  }
		 	}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	

	
	@Override
	public String uploadEventFile(MultipartFile[] files) throws IOException {
		Map<String,List<EventEmployeeInfo>> eventMap=new HashMap<String,List<EventEmployeeInfo>>();
		StringBuilder errorMessage = new StringBuilder();
		List<EventEmployeeInfo> eventEmployeeInfoList=null;
		  for(MultipartFile file : files) {
			  String fileName = file.getOriginalFilename();
			  FileInputStream inputStream=(FileInputStream) file.getInputStream();
			  if(fileName.startsWith("Outreach Events Summary")) {
				  errorMessage.append(saveEventSummaryDetails(inputStream));
			  }else if(fileName.startsWith("OutReach Event Information")) {
				  eventEmployeeInfoList= Utils.parseExcelFileToBeans(inputStream,FeedbackConstants.EVENT_EMPINFO_MAPPER);
				  eventMap.put(FeedbackConstants.PARTICIPATED, eventEmployeeInfoList);
			  }else  if(fileName.startsWith("Volunteer_Enrollment Details_Not_Attend")) {
				  eventEmployeeInfoList=Utils.parseExcelFileToBeans(inputStream,FeedbackConstants.EVENT_EMP_LIST);
				  eventMap.put(FeedbackConstants.NOTATTENDED, eventEmployeeInfoList);
			  }else {
				  eventEmployeeInfoList=Utils.parseExcelFileToBeans(inputStream,FeedbackConstants.EVENT_EMP_LIST);
				  eventMap.put(FeedbackConstants.UNREGISTERED, eventEmployeeInfoList);
			  }
			  if(inputStream!=null) {
				  inputStream.close(); 
			  }
		  }
		  if(!eventMap.isEmpty()) {
			  for(String eventStatus : eventMap.keySet()) {
				  errorMessage.append(validateEventEmpList(eventMap.get(eventStatus), eventStatus));
			  }
		  }
		  return errorMessage.toString();
	}
	
	

	private void saveEventFileUpload(EventEmployeeInfo eventEmployeeInfo,String eventStatus) {
		
		List<EventEmployeeInfo> list = eventEmployeeInfoRepository.findByEventId(eventEmployeeInfo.getEventEmployeeIdentity().getAssociateId(),
						eventEmployeeInfo.getEventEmployeeIdentity().getEventId());
	    if(list== null || list.isEmpty() ) {
					eventEmployeeInfo.setEventStatus(eventStatus);
					eventEmployeeInfo.setEmailCount(0);
					eventEmployeeInfo.setResponded("N");	
					eventEmployeeInfoRepository.save(eventEmployeeInfo);
		} 
	  

	}
	
	
	
	public String validateEventEmpList(List<EventEmployeeInfo> eventEmployeeInfoList,String eventStatus) {
		StringBuilder message = new StringBuilder();
		StringBuilder errorMessage = null;
		for(EventEmployeeInfo eventEmp : eventEmployeeInfoList) {
			 errorMessage=new StringBuilder();
			if(eventEmp.getEventEmployeeIdentity().getAssociateId()==0
					|| StringUtils.isEmpty(eventEmp.getEventEmployeeIdentity().getEventId())
					|| StringUtils.isEmpty(eventEmp.getBaseLocation())
					|| StringUtils.isEmpty(eventEmp.getEventDateString())) {
				errorMessage.append(" Mandatory fields missing AssociatId or EventId  or BaseLocation ");
			}else if(!eventSummaryDetailsRepository.findById(eventEmp.getEventEmployeeIdentity().getEventId()).isPresent()) {
				errorMessage.append("Event Details not found.");
			}  else if(eventEmp.getEventDateString()!=null) {
				Date eventDate = getEventDate(eventEmp.getEventDateString());
				if(eventDate!=null) eventEmp.setEventDate(eventDate); else errorMessage.append(" Invalid Event Date");
			}
			if(errorMessage.toString().isEmpty()) {
				saveEventFileUpload(eventEmp, eventStatus);
			}else {
				errorMessage.append("For Associate Id : "+eventEmp.getEventEmployeeIdentity().getAssociateId());
				errorMessage.append(" Event Id Id: "+eventEmp.getEventEmployeeIdentity().getEventId());
				message.append(errorMessage.toString());
				message.append("\t");
			}
		}
		return message.toString();
	}

	private String saveEventSummaryDetails(FileInputStream inputStream) {
		List<EventSummaryDetails> eventSummaryDetails =null;
		try {
			eventSummaryDetails = Utils.parseExcelFileToBeans(inputStream,FeedbackConstants.EVENT_SUMMARY_MAPPER);
			return validateEventSummaryList(eventSummaryDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(eventSummaryDetails);
		return null;
	}

	private String  validateEventSummaryList(List<EventSummaryDetails> eventSummaryList) {
		StringBuilder message = new StringBuilder();
		for(EventSummaryDetails eventSummary : eventSummaryList) {
			StringBuilder errorMessage=new StringBuilder();
			 if(StringUtils.isEmpty(eventSummary.getEventId())
					|| StringUtils.isEmpty(eventSummary.getMonth())
					|| StringUtils.isEmpty(eventSummary.getBaseLocation())
					|| StringUtils.isEmpty(eventSummary.getBeneficiaryName())
					|| StringUtils.isEmpty(eventSummary.getProject())
					|| StringUtils.isEmpty(eventSummary.getEventDateString())
					|| StringUtils.isEmpty(eventSummary.getEventName())
					|| StringUtils.isEmpty(eventSummary.getPocId())
					|| StringUtils.isEmpty(eventSummary.getPocName())) {
				 errorMessage.append(" Mandatory fields missing EventId  or BaseLocation "
				 		+ "or Beneficiary Name or Projct or Event Date or Event Name or POC Id or POC Name"); 
			 } else if(eventSummary.getEventDateString()!=null) {
				 Date eventDate = getEventDate(eventSummary.getEventDateString());
					if(eventDate!=null) eventSummary.setEventDate(eventDate); else errorMessage.append(" Invalid Event Date");
					try {
						 eventSummary.setPocContactNo(Integer.parseInt(eventSummary.getPocContactNoString()));
					} catch (NumberFormatException e) {
							errorMessage.append(" Invalid Poc Contact Number");
					} 
			 }
			
			if(errorMessage.toString().isEmpty()) {
				eventSummaryDetailsRepository.save(eventSummary);
			}else {
				errorMessage.append(" Event Id : "+eventSummary.getEventId());
				message.append(errorMessage.toString());
				message.append("\t");
			}
		}
		return message.toString();
	}
	
	private Date getEventDate(String eventDateString) {
		Date eventDate=null;
		try {
			eventDate = new SimpleDateFormat("dd-MM-yy").parse(eventDateString);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return eventDate;
	}
}
