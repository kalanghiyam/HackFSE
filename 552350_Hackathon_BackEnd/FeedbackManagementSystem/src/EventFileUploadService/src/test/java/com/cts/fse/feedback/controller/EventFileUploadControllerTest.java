package com.cts.fse.feedback.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.fse.feedback.bean.EmailTemplate;
import com.cts.fse.feedback.bean.EventEmployeeIdentity;
import com.cts.fse.feedback.bean.EventEmployeeInfo;
import com.cts.fse.feedback.dto.DashboardReportDTO;
import com.cts.fse.feedback.dto.EventEmployeeInfoDTO;
import com.cts.fse.feedback.dto.UserRoleDTO;
import com.cts.fse.feedback.repository.EmailTemplateRepository;
import com.cts.fse.feedback.repository.EventEmployeeInfoRepository;
import com.cts.fse.feedback.service.EmailTemplateService;
import com.cts.fse.feedback.service.EventFileUploadService;
import com.cts.fse.feedback.service.FileUploadValidationService;
import com.cts.fse.feedback.utils.FeedbackConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringRunner.class)
@WebMvcTest(value=EventFileUploadController.class,secure = false)
public class EventFileUploadControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmailTemplateService emailTemplateService;
	
	@MockBean
	private EventFileUploadService eventFileUploadService;
	
	@MockBean
	private FileUploadValidationService fileUploadValidationService;
	
	@MockBean private EventEmployeeInfoRepository eventEmployeeInfoRepository;
	
	@MockBean
	private EmailTemplateRepository emailTemplateRepository;
	
	@MockBean
	private JavaMailSender sender;
	
	@Test
	public void testGetTemplateList() throws Exception{
		EmailTemplate mockEmailTemplate =new EmailTemplate();
		mockEmailTemplate.setStatus("PARTICIPATED");
		mockEmailTemplate.setEmailTemplate("Body of the content");
		mockEmailTemplate.setEmailNotification(1);
		mockEmailTemplate.setNotificationInterval(2);
		List<EmailTemplate> emailTemplateList = new ArrayList<EmailTemplate>();
		emailTemplateList.add(mockEmailTemplate);
		Mockito.when(emailTemplateService.get()).thenReturn(emailTemplateList);
		String URI = "/emailTemplate/get";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(emailTemplateList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	public void testUpdate() throws Exception {
		EmailTemplate mockEmailTemplate =new EmailTemplate();
		mockEmailTemplate.setStatus("PARTICIPATED");
		mockEmailTemplate.setEmailTemplate("Body of the content");
		mockEmailTemplate.setEmailNotification(1);
		mockEmailTemplate.setNotificationInterval(2);
		String inputInJson = this.mapToJson(mockEmailTemplate);
		String URI = "/emailTemplate/update";
		Mockito.when(emailTemplateService.update(Mockito.any(EmailTemplate.class))).thenReturn(mockEmailTemplate);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(URI).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
	}	
	
	@Test
	public void testSendEmailToAssociates() throws Exception {
		List<EventEmployeeInfoDTO> eventEmpList = new ArrayList<EventEmployeeInfoDTO>();
		EventEmployeeInfoDTO eventEmp=new EventEmployeeInfoDTO();
		eventEmp.setAssociateId(552351);
		eventEmp.setEventId("EVENT7461");
		eventEmp.setAssociateName("Gowthami");
		eventEmp.setBu("BFS");
		eventEmp.setSelect(true);
		eventEmpList.add(eventEmp);
		
		EmailTemplate mockEmailTemplate =new EmailTemplate();
		mockEmailTemplate.setStatus("PARTICIPATED");
		mockEmailTemplate.setEmailTemplate("Body of the content");
		mockEmailTemplate.setEmailNotification(1);
		mockEmailTemplate.setNotificationInterval(2);
		
		List<EmailTemplate> emailTemplateList = new ArrayList<EmailTemplate>();
		emailTemplateList.add(mockEmailTemplate);
		
		Iterable<EmailTemplate> iterableList = (Iterable<EmailTemplate>) emailTemplateList;
		String inputInJson = this.mapToJson(emailTemplateList);
		
		Mockito.doNothing().when(emailTemplateService).sendRemainderEmail(eventEmpList);

		Mockito.when(emailTemplateRepository.findAll()).thenReturn(iterableList);
		String URI = "/emailTemplate/sendEmail";
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(URI).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		String inputString="You successfully uploaded ";
		assertThat(outputInJson).isEqualTo(inputString);
 	}
	  
	@Test
	public void testFetchReport() throws Exception{
	 DashboardReportDTO dashboardReportDTO =new DashboardReportDTO();
	 dashboardReportDTO.setEventId("EVENT74261");
	 dashboardReportDTO.setAverageSmileyCount(new BigDecimal("4"));
	 List<DashboardReportDTO> dashboardReportList = new ArrayList<DashboardReportDTO>();
	 dashboardReportList.add(dashboardReportDTO);
	 Mockito.when(eventFileUploadService.getEventDashboardReport("Event POC","5523")).thenReturn(dashboardReportList);
	 String URI = "/getDashboardReport/Event POC/5523";
	 RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
	 MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 String expectedJson = this.mapToJson(dashboardReportList);
	 String outputInJson = result.getResponse().getContentAsString();
	 assertThat(outputInJson).isEqualTo(expectedJson);
	}
	 
	  @Test
	  public void testGetSearchList() throws Exception {
		    List<EventEmployeeInfoDTO> eventEmpList = new ArrayList<EventEmployeeInfoDTO>();
			EventEmployeeInfoDTO eventEmp=new EventEmployeeInfoDTO();
			eventEmp.setAssociateId(552351);
			eventEmp.setEventId("EVENT7461");
			eventEmp.setAssociateName("Gowthami");
			eventEmp.setBu("BFS");
			eventEmp.setSelect(true);
			eventEmpList.add(eventEmp);
			Mockito.when(eventFileUploadService.getSearchList("PARTICIPATED")).thenReturn(eventEmpList);
			String URI = "/getSearchList/PARTICIPATED";
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			String expectedJson = this.mapToJson(eventEmpList);
			String outputInJson = result.getResponse().getContentAsString();
			assertThat(outputInJson).isEqualTo(expectedJson);
	  }
	  
	  @Test
	  public void testGetEventDropDownList() throws Exception{
		  List<String> eventList=new ArrayList<String>();
		  eventList.add("event1");
		  Iterable<String> iterbaleList=eventList;
		  Mockito.when(eventFileUploadService.getEventDropDownList()).thenReturn(eventList);
		  String URI = "/getDropDownList";
		  RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		  MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		  String expectedJson = this.mapToJson(iterbaleList);
		  String outputInJson = result.getResponse().getContentAsString();
		  assertThat(outputInJson).isEqualTo(expectedJson);
	  }
		 
	  @Test
	  public void testgetSendMailEventList() throws Exception {
		List<EventEmployeeInfoDTO> eventEmpList = new ArrayList<EventEmployeeInfoDTO>();
		EventEmployeeInfoDTO eventEmp=new EventEmployeeInfoDTO();
		eventEmp.setAssociateId(552351);
		eventEmp.setEventId("EVENT7461");
		eventEmp.setAssociateName("Gowthami");
		eventEmp.setBu("BFS");
		eventEmp.setSelect(true);
		eventEmpList.add(eventEmp);
		Mockito.when(eventFileUploadService.getSendMailEventList()).thenReturn(eventEmpList);
		String URI = "/getEmailList";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(eventEmpList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	  }
	  
	
	  @Test
	  public void testGetEventEmpList() throws Exception {
		  Mockito.when(eventFileUploadService.getEventEmpList(Mockito.anyInt())).thenReturn(getEventEmpList());
		  EventEmployeeInfo eventStatus = getEventEmpList().get(0);
		  UserRoleDTO userRoleDTO = new UserRoleDTO();
  			userRoleDTO.setAssociateId(eventStatus.getEventEmployeeIdentity().getAssociateId());
  			userRoleDTO.setEventId(eventStatus.getEventEmployeeIdentity().getEventId());
  			userRoleDTO.setEventStatus(eventStatus.getEventStatus());
  			userRoleDTO.setResponded(eventStatus.getResponded());
  			userRoleDTO.setAssociateName(eventStatus.getAssociateName());
  			userRoleDTO.setRole(FeedbackConstants.ROLE);
		  String URI = "/getUserEventDetails/552350";
		  RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		  MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		  String expectedJson = this.mapToJson(userRoleDTO);
		  String outputInJson = result.getResponse().getContentAsString();
		  assertThat(outputInJson).isEqualTo(expectedJson);
	  }

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	

	private List<EventEmployeeInfo> getEventEmpList(){
		List<EventEmployeeInfo> eventEmpList = new ArrayList<EventEmployeeInfo>();
		EventEmployeeInfo eventEmp=new EventEmployeeInfo();
		eventEmp.setEventEmployeeIdentity(new EventEmployeeIdentity());
		eventEmp.getEventEmployeeIdentity().setAssociateId(552351);
		eventEmp.getEventEmployeeIdentity().setEventId("EVENT7461");
		eventEmp.setAssociateName("Gowthami");
		eventEmp.setBu("BFS");
		eventEmpList.add(eventEmp);
		return eventEmpList;
	}
}

