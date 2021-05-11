package com.cts.fse.feedback.schedule;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class SchedulerConfig {

	@Bean
	public JobDetail jobDetail() {
	    return JobBuilder.newJob().ofType(EventFileUploadJob.class)
	      .storeDurably()
	      .withIdentity("Qrtz_Job_Detail")  
	      .withDescription("Invoke Sample Job service...")
	      .build();
	}
	
	
	@Bean
	public SimpleTriggerFactoryBean trigger() {
	    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	    trigger.setJobDetail(jobDetail());
	    trigger.setRepeatInterval(3500000);
	    trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    return trigger;
	}
	
}
