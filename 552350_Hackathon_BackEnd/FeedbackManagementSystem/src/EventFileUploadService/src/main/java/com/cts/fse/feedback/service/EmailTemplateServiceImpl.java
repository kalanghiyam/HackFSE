package com.cts.fse.feedback.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.fse.feedback.bean.EmailTemplate;
import com.cts.fse.feedback.bean.EventEmployeeInfo;
import com.cts.fse.feedback.dto.EventEmployeeInfoDTO;
import com.cts.fse.feedback.repository.EmailTemplateRepository;
import com.cts.fse.feedback.repository.EventEmployeeInfoRepository;

@Service
@Transactional
public class EmailTemplateServiceImpl implements EmailTemplateService{

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	
	@Autowired private EventEmployeeInfoRepository eventEmployeeInfoRepository;
	

	
	@Override
	public Iterable<EmailTemplate> get() {
		return emailTemplateRepository.findAll();
	}

	
	@Override
	public EmailTemplate update(EmailTemplate emailTemplate) {
		return emailTemplateRepository.save(emailTemplate);
	}


	@Override
	public void sendMailToAssociates() throws Exception {
	     Map<String,EmailTemplate> templateMap= getMailTemplateList();
	     for(String key:templateMap.keySet()) {
	      		EmailTemplate emailTemplate = templateMap.get(key);
	            List<EventEmployeeInfo> eventEmployeeList =eventEmployeeInfoRepository.getEventEmpEmailList
	        				(emailTemplate.getEmailNotification(), "N",emailTemplate.getStatus());
	           
	        	for(EventEmployeeInfo eventEmployeeInfo : eventEmployeeList) {
	        		Date emailSendDate = eventEmployeeInfo.getEmailSendDate();
	        		if(eventEmployeeInfo.getEmailSendDate()!=null) {
	        			if(validateDate(emailSendDate, emailTemplate.getNotificationInterval())) {
	        				sendEmail(emailTemplate.getEmailTemplate(), eventEmployeeInfo);
	        			}
	        		}else {
	        			sendEmail(emailTemplate.getEmailTemplate(), eventEmployeeInfo);
	        		}
	        	}
	      }
	}
	
	private boolean validateDate(Date emailSendDate,int notifcationInterval) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(emailSendDate); 
		c.add(Calendar.DATE, notifcationInterval); // Adding 2/3/5 days
		Date date = sdf.parse(sdf.format(c.getTime()));
		emailSendDate = sdf.parse(sdf.format(emailSendDate));
		if(emailSendDate.before((date))) {
			return true;
		}
		return false;
	}
	
	private void sendEmail(String emailTemplate,EventEmployeeInfo eventEmployeeInfo) {
	  try {
			MimeMessage message = sender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo("gow.cute@gmail.com");
		helper.setText(emailTemplate);
		helper.setSubject("Feedback Survey for Event "+eventEmployeeInfo.getEventEmployeeIdentity().getEventId());
		sender.send(message);
		eventEmployeeInfo.setEmailSendDate(new Date());
		int emailCount=eventEmployeeInfo.getEmailCount();
		eventEmployeeInfo.setEmailCount(++emailCount);
		eventEmployeeInfoRepository.save(eventEmployeeInfo);
	  } catch (MessagingException e) {
          e.printStackTrace();
      }
	}
	
	public void sendRemainderEmail(List<EventEmployeeInfoDTO> responseDetails) throws Exception {
		Map<String,EmailTemplate> templateMap= getMailTemplateList();
	     for(EventEmployeeInfoDTO eventEmployeeInfoDTO:responseDetails) {
	    	if(eventEmployeeInfoDTO.getSelect()) {
	    	String emailTemplate = templateMap.get(eventEmployeeInfoDTO.getEventStatus()).getEmailTemplate();
	    	MimeMessage message = sender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setTo("gow.cute@gmail.com");
			helper.setText(emailTemplate);
			helper.setSubject("Remainder Mail - Feedback Survey for Event "+eventEmployeeInfoDTO.getEventId());
			sender.send(message);
			
	    	 }
	     }
	}
	
	private Map<String,EmailTemplate> getMailTemplateList(){
		Map<String,EmailTemplate> templateMap=new HashMap<String, EmailTemplate>();
		Iterable<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
		for(EmailTemplate emailTemplate:emailTemplateList) {
			templateMap.put(emailTemplate.getStatus(),emailTemplate);
		}
		return templateMap;
	}

}
