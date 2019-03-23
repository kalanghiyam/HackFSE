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

import com.cts.fse.feedback.bean.FeedbackDetails;
import com.cts.fse.feedback.service.FeedBackDetaillsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringRunner.class)
@WebMvcTest(value=FeedbackDetailsController.class,secure = false)
public class FeedbackDetailsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FeedBackDetaillsService feedBackService;
	

	@Test
	public void testCreate() throws Exception {
		String inputInJson = this.mapToJson(getFeedbackDetails());
		String URI = "/feedBackDetails/create";
		Mockito.when(feedBackService.createFeedBackQuestion(Mockito.any(FeedbackDetails.class))).thenReturn(getFeedbackDetails());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON).content(inputInJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}
	
	@Test
	public void testUpdate() throws Exception {
		String inputInJson = this.mapToJson(getFeedbackDetails());
		String URI = "/feedBackDetails/update";
		Mockito.when(feedBackService.update(Mockito.any(FeedbackDetails.class))).thenReturn(getFeedbackDetails());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputInJson);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}	
	
	@Test
	public void testGetAll() throws Exception{
		List<FeedbackDetails> feedbackList = new ArrayList<FeedbackDetails>();
		feedbackList.add(getFeedbackDetails());
		Mockito.when(feedBackService.getFeedBackDetails()).thenReturn(feedbackList);
		String URI = "/feedBackDetails/get";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(feedbackList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
 
	@Test
	public void testGetAllUser() throws Exception{
		List<FeedbackDetails> feedbackList = new ArrayList<FeedbackDetails>();
		feedbackList.add(getFeedbackDetails());
		Mockito.when(feedBackService.getFeedBackDetails()).thenReturn(feedbackList);
		String URI = "/feedBackDetails/get";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(feedbackList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetFeedbackQuestion() throws Exception{
		List<FeedbackDetails> feedbackList = new ArrayList<FeedbackDetails>();
		feedbackList.add(getFeedbackDetails());
		Mockito.when(feedBackService.getFeedBackQuestion(Mockito.anyString())).thenReturn(feedbackList);
		String URI = "/feedBackDetails/getDetails/5";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(feedbackList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	@Test
	public void testDelete() throws Exception{
		Mockito.doNothing().when(feedBackService).deleteFeedBack(552);
		String URI = "/feedBackDetails/552";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}

	private FeedbackDetails getFeedbackDetails(){
		FeedbackDetails feedback =new FeedbackDetails();
		feedback.setFeedBackDesc("What about this Volunterring activity?");
		feedback.setId(1);
		feedback.setInputType("Radio");
		feedback.setStatus("PARTICIPATED");
		return feedback;
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}

