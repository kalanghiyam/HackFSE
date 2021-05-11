package com.cts.fse.feedback.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import com.cts.fse.feedback.service.EmailTemplateService;
import com.cts.fse.feedback.service.FileUploadValidationService;

public class EventFileUploadJob implements Job {
 
    @Autowired
    private FileUploadValidationService fileUploadJobService;
    
    @Autowired
    private EmailTemplateService emailTemplateService;
 
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	System.out.println("Candidjava welcomes simple job");
    	fileUploadJobService.executeInputEventFile();
    	try {
			emailTemplateService.sendMailToAssociates();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
