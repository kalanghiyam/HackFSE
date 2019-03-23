package com.cts.fse.feedback.service;

import com.cts.fse.feedback.bean.EventSummaryDetails;
import com.cts.fse.feedback.bean.FeedbackDetails;

public interface FeedBackDetaillsService {
	public FeedbackDetails createFeedBackQuestion(FeedbackDetails feedbackDetails);
	public Iterable<FeedbackDetails> getFeedBackDetails();
	public FeedbackDetails update(FeedbackDetails feedbackDetails);
	public void deleteFeedBack(Integer id);
	public Iterable<FeedbackDetails> getFeedBackQuestion(String eventStatus);
	public Iterable<EventSummaryDetails> getSummary();  
	public Iterable<String> getLocations();
	public Iterable<String> getProjects();
	public Iterable<EventSummaryDetails> searchSummary(String location, String project);
}

