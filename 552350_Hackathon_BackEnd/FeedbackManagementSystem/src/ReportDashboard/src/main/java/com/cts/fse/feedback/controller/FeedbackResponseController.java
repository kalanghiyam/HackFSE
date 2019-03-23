package com.cts.fse.feedback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fse.feedback.bean.DashboardReportDTO;
import com.cts.fse.feedback.bean.ResponseSummaryDTO;
import com.cts.fse.feedback.service.FeedbackResponseService;

@CrossOrigin(allowedHeaders="*",origins="*")
@RestController
public class FeedbackResponseController {

		@Autowired
		private FeedbackResponseService eventFeedbackResponseService;
		
	    @PutMapping(value="feedBackDetails/saveResponse/{id}/{eventId}", headers="Accept=application/json")
	    public ResponseEntity <String> saveEventFeedbackResponse(@PathVariable("id") int associateId,@PathVariable("eventId") String eventId,
	    		@RequestBody List<com.cts.fse.feedback.bean.EventResponseDTO> responseDetails) {
	    	String message="";
	    	 try {
	    		 message = "Saved successfully ";
	    		 eventFeedbackResponseService.saveEventFeedBackResponse(responseDetails, eventId, associateId);
	    		 return ResponseEntity.status(HttpStatus.OK).body(message);
	    	 } catch (Exception e) {
			   message = "Fail to save"+e.getMessage();
			   return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	    	 }
	    }
	    


		@GetMapping(value="/getSimleyReport/{role}/{associateId}" , produces=MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody List<DashboardReportDTO> fetchReport(@PathVariable("role") String role,
				@PathVariable("associateId") String associateId){
			return eventFeedbackResponseService.getSmileyReport(role,associateId);   
		}
		
		
		@GetMapping(value="/getResponseList" , produces=MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody List<DashboardReportDTO> getFeedbackRespondedList(){
			return eventFeedbackResponseService.getFeedbackRespondedList();
		}
		
		
		@GetMapping(value="/getResponse" , produces=MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody List<ResponseSummaryDTO> getResponseSummary(){
			return eventFeedbackResponseService.getResponseSummary();
		}
		
}
