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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.fse.feedback.bean.UserRoleDetails;
import com.cts.fse.feedback.jwt.security.JwtProvider;
import com.cts.fse.feedback.service.UserRoleDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RunWith(SpringRunner.class)
@WebMvcTest(value=UserRoleDetailsController.class,secure = false)
public class UserRoleDetailsControllerTest {

	@MockBean
	private UserRoleDetailsService userRoleDetailsService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	JwtProvider jwtProvider;
	
	@MockBean
    PasswordEncoder passwordEncoder;
	
	@Test
	public void testCreateUser() throws Exception {
		String inputInJson = this.mapToJson(getUserRoleDetails());
		String URI = "/userDetails/create";
		Mockito.when(userRoleDetailsService.createUser(Mockito.any(UserRoleDetails.class))).thenReturn(getUserRoleDetails());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON).content(inputInJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		String inputInJson = this.mapToJson(getUserRoleDetails());
		String URI = "/userDetails/update";
		Mockito.when(userRoleDetailsService.findById(Mockito.any())).thenReturn(getUserRoleDetails());
		Mockito.when(userRoleDetailsService.update(Mockito.any(UserRoleDetails.class))).thenReturn(getUserRoleDetails());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputInJson);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
	}	
	
	@Test
	public void testGetAllUser() throws Exception{
		List<UserRoleDetails> userRoleList = new ArrayList<UserRoleDetails>();
		userRoleList.add(getUserRoleDetails());
		Mockito.when(userRoleDetailsService.getUser()).thenReturn(userRoleList);
		String URI = "/userDetails/get";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(userRoleList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
 
	@Test
	public void testDeleteUser() throws Exception{
		Mockito.doNothing().when(userRoleDetailsService).deleteUser(552);
		String URI = "/userDetails/552";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}

	private UserRoleDetails getUserRoleDetails(){
		UserRoleDetails mockUser =new UserRoleDetails();
		mockUser.setAssociateId(552350);
		mockUser.setAssociateName("Kuppuswamy");
		mockUser.setRole("Admin");
		return mockUser;
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}

