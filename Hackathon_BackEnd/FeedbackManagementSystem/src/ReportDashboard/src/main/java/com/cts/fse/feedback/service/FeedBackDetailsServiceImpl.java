package com.cts.fse.feedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.fse.feedback.bean.EventSummaryDetails;
import com.cts.fse.feedback.bean.FeedbackDetails;
import com.cts.fse.feedback.repository.EventSummaryRepository;
import com.cts.fse.feedback.repository.FeedbackDetailsRepository;

@Service
@Transactional
public class FeedBackDetailsServiceImpl implements FeedBackDetaillsService{

	@Autowired
	private FeedbackDetailsRepository  feedbackDetailsRepository;
	
	@Autowired
	private EventSummaryRepository  summaryrepository; 
	
	@Override
	public FeedbackDetails createFeedBackQuestion(FeedbackDetails feedbackDetails) {
		return feedbackDetailsRepository.save(feedbackDetails);
	}

	@Override
	public Iterable<FeedbackDetails> getFeedBackDetails() {
		return feedbackDetailsRepository.findAll();
	}

	@Override
	public FeedbackDetails update(FeedbackDetails feedbackDetails) {
		return feedbackDetailsRepository.save(feedbackDetails);
	}

	@Override
	public void deleteFeedBack(Integer id) {
		feedbackDetailsRepository.deleteById(id);
	}

	@Override
	public Iterable<FeedbackDetails> getFeedBackQuestion(String eventStatus) {
		return feedbackDetailsRepository.getFeedbackList(eventStatus);
	}

	@Override
	public Iterable<EventSummaryDetails> getSummary() {
		return summaryrepository.findAll();
	}  
	
	@Override  
	public Iterable<String> getLocations(){
		return summaryrepository.getLocations(); 
	}   
	
	@Override
	public Iterable<String> getProjects(){   
		return summaryrepository.getProjects();
	}   
	
	@Override
	public Iterable<EventSummaryDetails> searchSummary(String location, String project) {
		if(project.equals("undefined") && !location.equals("undefined")) {
			return summaryrepository.searchSummaryLocation(location); 
		}
		
		else if(!project.equals("undefined") && location.equals("undefined")) {  
			return summaryrepository.searchSummaryProject(project);  
		}
		
		else if((project.equals("undefined") && location.equals("undefined")) | ((project.equals(null) && location.equals(null)))) {
			return summaryrepository.findAll();  
		}
		else if (!project.equals("undefined") && !location.equals("undefined")){
			return summaryrepository.searchSummary(location,project);
		}
		else { 
			return summaryrepository.searchSummary(location,project);  
		}
	}
}
