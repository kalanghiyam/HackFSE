package com.cts.fse.feedback.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cts.fse.feedback.bean.EventSummaryDetails;

@Repository
public interface EventSummaryDetailsRepository extends CrudRepository<EventSummaryDetails, String>{

}
