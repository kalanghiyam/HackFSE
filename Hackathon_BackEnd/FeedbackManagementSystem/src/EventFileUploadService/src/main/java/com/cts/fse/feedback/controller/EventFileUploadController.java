package com.cts.fse.feedback.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cts.fse.feedback.bean.EmailTemplate;
import com.cts.fse.feedback.bean.EventEmployeeInfo;
import com.cts.fse.feedback.dto.DashboardReportDTO;
import com.cts.fse.feedback.dto.EventEmployeeInfoDTO;
import com.cts.fse.feedback.dto.UserRoleDTO;
import com.cts.fse.feedback.service.EmailTemplateService;
import com.cts.fse.feedback.service.EventFileUploadService;
import com.cts.fse.feedback.service.FileUploadValidationService;
import com.cts.fse.feedback.utils.FeedbackConstants;


@CrossOrigin(allowedHeaders="*",origins="*")
@RestController
public class EventFileUploadController {

	@Autowired
	private EventFileUploadService eventFileUploadService;
	
	@Autowired
	private FileUploadValidationService fileUploadValidationService;
	

	 @Autowired
	 private EmailTemplateService emailTemplateService;
	
	 @PostMapping("/fileUpload")
	 public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile[] files) {
	  String message = "";
	  try {
		  message  =  (String) fileUploadValidationService.uploadEventFile(files);
		  return ResponseEntity.status(HttpStatus.OK).body(message);
	  } catch (Exception e) {
		  message = "Error in uploading the File";
		  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	  }
	 }
	 
	 @GetMapping(value="getUserEventDetails/{id}", headers="Accept=application/json")
	 public  @ResponseBody UserRoleDTO getEventEmpList(@PathVariable("id") int id) {
	    	List<EventEmployeeInfo> infoList= eventFileUploadService.getEventEmpList(id);
	    	UserRoleDTO userRoleDTO=null;
	    	if(infoList!=null && !infoList.isEmpty()) {
	    		EventEmployeeInfo eventStatus=infoList.get(0);
	    		userRoleDTO = new UserRoleDTO();
	    		userRoleDTO.setAssociateId(eventStatus.getEventEmployeeIdentity().getAssociateId());
	    		userRoleDTO.setEventId(eventStatus.getEventEmployeeIdentity().getEventId());
	    		userRoleDTO.setEventStatus(eventStatus.getEventStatus());
	    		userRoleDTO.setResponded(eventStatus.getResponded());
	    		userRoleDTO.setAssociateName(eventStatus.getAssociateName());
	    		userRoleDTO.setRole(FeedbackConstants.ROLE);
	    	}
			return userRoleDTO;
	 }
	 

	 @GetMapping(value="/getDropDownList", headers="Accept=application/json")
	 public @ResponseBody Iterable<String> getEventDropDownList() {	 
	  return eventFileUploadService.getEventDropDownList();
	 }
	 
	 @GetMapping(value="/getEmailList", headers="Accept=application/json")
	 public @ResponseBody Iterable<EventEmployeeInfoDTO> getSendMailEventList() {	 
	  return eventFileUploadService.getSendMailEventList();
	 }
	  
	 @GetMapping(value="getSearchList/{eventStatus}", headers="Accept=application/json")
	 public   @ResponseBody List<EventEmployeeInfoDTO> getSearchList(@PathVariable("eventStatus") String eventStatus) {
		    	return eventFileUploadService.getSearchList(eventStatus);
	}
	    
	 @GetMapping(value="/getDashboardReport/{role}/{associateId}" , produces=MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody List<DashboardReportDTO> fetchReport(@PathVariable("role") String role,
			 @PathVariable("associateId") String associateId){
			return eventFileUploadService.getEventDashboardReport(role,associateId);   
	}
	   
	    @GetMapping(value="/emailTemplate/get", headers="Accept=application/json")
		 public Iterable<EmailTemplate> getTemplateList() {
				return emailTemplateService.get();
		 }
		    
		 @PutMapping(value="/emailTemplate/sendEmail", headers="Accept=application/json")
		 public ResponseEntity < String > sendEmailToAssociates(@RequestBody List<EventEmployeeInfoDTO> responseDetails) {
	         String message="";
			 try {
				   emailTemplateService.sendRemainderEmail(responseDetails);
				   message = "You successfully uploaded ";
				   return ResponseEntity.status(HttpStatus.OK).body(message);
			 } catch (Exception e) {
				   message = "Fail to upload Profile Picture";
				   return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		     }
		  }
		  
		 @PutMapping(value="/emailTemplate/update")
		 public EmailTemplate update(@RequestBody EmailTemplate emailTemplate) {
			 return  emailTemplateService.update(emailTemplate);
		 }
}