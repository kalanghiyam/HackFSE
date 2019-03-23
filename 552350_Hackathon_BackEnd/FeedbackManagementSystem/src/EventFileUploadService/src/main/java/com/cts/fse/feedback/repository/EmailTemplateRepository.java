package com.cts.fse.feedback.repository;

import org.springframework.data.repository.CrudRepository;

import com.cts.fse.feedback.bean.EmailTemplate;

public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, String>{

}
