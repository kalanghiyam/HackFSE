package com.cts.fse.feedback.service;

import java.util.List;

import com.cts.fse.feedback.bean.EmailTemplate;
import com.cts.fse.feedback.dto.EventEmployeeInfoDTO;

public interface EmailTemplateService {
	
	
	public Iterable<EmailTemplate> get();
	public EmailTemplate update(EmailTemplate emailTemplate);
	public void sendMailToAssociates() throws Exception;
	public void sendRemainderEmail(List<EventEmployeeInfoDTO> responseDetails) throws Exception;
}
