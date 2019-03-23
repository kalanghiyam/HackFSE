package com.cts.fse.feedback.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.fse.feedback.bean.DashboardReportDTO;
import com.cts.fse.feedback.bean.EventResponseDTO;
import com.cts.fse.feedback.service.FeedbackResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value=FeedbackResponseController.class,secure = false)
public class FeedbackResponseControllerTest {

	@MockBean
	private FeedbackResponseService eventFeedbackResponseService;
	
	@Autowired
	private MockMvc mockMvc;
	
    
	@Test
	public void testSaveEventFeedbackResponse() throws Exception {
		List<EventResponseDTO> responseDetails=new ArrayList<EventResponseDTO>();
		EventResponseDTO eventResponseDTO=new EventResponseDTO();
		eventResponseDTO.setFeedbackResponse("The Event is good and we were excited");
		eventResponseDTO.setId(2);
		responseDetails.add(eventResponseDTO);
		String inputInJson = this.mapToJson(responseDetails);
		String URI = "/feedBackDetails/saveResponse/55235/EVENT72451";
		Mockito.doNothing().when(eventFeedbackResponseService).saveEventFeedBackResponse(Mockito.anyList(), Mockito.anyString(), Mockito.anyInt());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputInJson);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testFetchReport() throws Exception{
		List<DashboardReportDTO> dashboardReportDTOList = new ArrayList<DashboardReportDTO>();
		DashboardReportDTO dashboardReportDTO = new DashboardReportDTO();
		dashboardReportDTO.setEventId("EVENT7461");
		dashboardReportDTO.setNotAttendedCount(10);
		dashboardReportDTO.setParticipatedCount(12);
		dashboardReportDTOList.add(dashboardReportDTO);
		Mockito.when(eventFeedbackResponseService.getSmileyReport("POC","5523")).thenReturn(dashboardReportDTOList);
		String URI = "/getSimleyReport/POC/5523";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(dashboardReportDTOList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
