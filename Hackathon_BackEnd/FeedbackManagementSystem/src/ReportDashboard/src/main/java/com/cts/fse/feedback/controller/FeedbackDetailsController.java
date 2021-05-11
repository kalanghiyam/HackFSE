package com.cts.fse.feedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.fse.feedback.bean.EventSummaryDetails;
import com.cts.fse.feedback.bean.FeedbackDetails;
import com.cts.fse.feedback.service.FeedBackDetaillsService;

@CrossOrigin(allowedHeaders="*",origins="*")
@RestController
public class FeedbackDetailsController {

	 @Autowired
	 private FeedBackDetaillsService feedBackService;
	 

	 @GetMapping(value="feedBackDetails/get", headers="Accept=application/json")
	 public @ResponseBody Iterable<FeedbackDetails> getAll() {	 
	  return feedBackService.getFeedBackDetails();
	 }
	 
     @PostMapping(value="feedBackDetails/create")
	 public ResponseEntity<String>  create(@RequestBody FeedbackDetails feedbackDetails){
    	feedBackService.createFeedBackQuestion(feedbackDetails);	
    	return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	 }
 
	@PutMapping(value="/feedBackDetails/update")
	public ResponseEntity<String> update(@RequestBody FeedbackDetails feedbackDetails) {
		feedBackService.update(feedbackDetails);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@DeleteMapping(value="/feedBackDetails/{id}", headers ="Accept=application/json")
	public ResponseEntity<FeedbackDetails> delete(@PathVariable("id") Integer id){
		feedBackService.deleteFeedBack(id);
		return new ResponseEntity<FeedbackDetails>(HttpStatus.NO_CONTENT);
	}

    @GetMapping(value="/feedBackDetails/getDetails/{id}", headers="Accept=application/json")
    public  @ResponseBody Iterable<FeedbackDetails> getFeedbackQuestion(@PathVariable("id") String eventStatus) {
		return feedBackService.getFeedBackQuestion(eventStatus);
    }
    
    @GetMapping(value="/getSummaryReport" , produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Iterable<EventSummaryDetails> fetchSummaryReport(){
		return feedBackService.getSummary();     
	}

	@GetMapping(value="/getLocation" , produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Iterable<String> fetchLocation(){
		return feedBackService.getLocations();   
	}

	@GetMapping(value="/getProject" , produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Iterable<String> fetchProject(){  
		return feedBackService.getProjects();            
	}
	
	@GetMapping(value="/searchSummary/{location}/{project}" , produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Iterable<EventSummaryDetails> searchSummary(@PathVariable("location") String location,
			@PathVariable("project") String project){  
		return feedBackService.searchSummary(location,project);          
	}
	
	
  
}
