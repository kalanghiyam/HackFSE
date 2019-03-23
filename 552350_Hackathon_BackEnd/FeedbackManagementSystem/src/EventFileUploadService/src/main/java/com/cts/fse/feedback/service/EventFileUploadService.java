package com.cts.fse.feedback.service;

import java.util.List;
import com.cts.fse.feedback.bean.EventEmployeeInfo;
import com.cts.fse.feedback.dto.DashboardReportDTO;
import com.cts.fse.feedback.dto.EventEmployeeInfoDTO;

public interface EventFileUploadService {
	
	public List<EventEmployeeInfo> getEventEmpList(int associateId);
	public List<EventEmployeeInfoDTO> getSendMailEventList();
	public List<String> getEventDropDownList();
	public List<EventEmployeeInfoDTO> getSearchList(String eventId);
	public List<DashboardReportDTO> getEventDashboardReport(String role,String associateId);

}
