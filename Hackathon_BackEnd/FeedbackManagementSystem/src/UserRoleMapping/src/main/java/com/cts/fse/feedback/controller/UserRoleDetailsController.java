package com.cts.fse.feedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.fse.feedback.bean.UserRoleDetails;
import com.cts.fse.feedback.jwt.message.response.JwtResponse;
import com.cts.fse.feedback.jwt.request.LoginForm;
import com.cts.fse.feedback.jwt.security.JwtProvider;
import com.cts.fse.feedback.jwt.security.UserPrinciple;
import com.cts.fse.feedback.service.UserRoleDetailsService;

@CrossOrigin(allowedHeaders="*",origins="*")
@EnableDiscoveryClient
@RestController
public class UserRoleDetailsController {

	@Autowired
	private UserRoleDetailsService userRoleDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@PostMapping("/userDetails/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserPrinciple userDetails =  (UserPrinciple) authentication.getPrincipal();            
		//check if this account is confirmed, ccode will be empty if 
		//user account is confirmed
		  /*UserRoleDetails user = userRoleDetailsService.findById(loginRequest.getUsername());*/
		  System.out.println("user login " + userDetails);
		if(userDetails!=null){
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(),userDetails.getRole(),
					userDetails.getEventId(),userDetails.getEventStatus(),userDetails.getResponded()));
		}else
		{
			return ResponseEntity.ok("{status: \"Confirmation Pending\"}");
		}
	}
	

    @PostMapping(value="/userDetails/create")
	 public ResponseEntity<String>  createUser(@RequestBody UserRoleDetails user){
    	  String message = "";
    	UserRoleDetails userData =  userRoleDetailsService.createUser(user);	
    	if (userData!=null) {
    		message="User Details Saved Successfully";
    		 return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			message = "Error in uploading the File";
			  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
    	
	 }
 
    @PostMapping("/userDetails/fileUpload")
	 public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile files) {
	  String message = "";
	  try {
		   userRoleDetailsService.uploadUserRoleDetails(files);
		   message = "User Details Saved Successfully";
		  return ResponseEntity.status(HttpStatus.OK).body(message);
	  } catch (Exception e) {
		  message = "Error in uploading the File";
		  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	  }
	 }
    
	 @GetMapping(value="/userDetails/get", headers="Accept=application/json")
	 public @ResponseBody Iterable<UserRoleDetails> getAllUser() {	 
	  return userRoleDetailsService.getUser();
	 }

	 @PutMapping(value="/userDetails/update")
	 public ResponseEntity<UserRoleDetails> updateUser(
			@RequestBody UserRoleDetails currentUser) {
		UserRoleDetails userData = userRoleDetailsService.findById(currentUser.getAssociateId());
		if (userData!=null) {
			userData.setAssociateId(currentUser.getAssociateId());
			userData.setAssociateName(currentUser.getAssociateName());
			userData.setRole(currentUser.getRole());
			return new ResponseEntity<>(userRoleDetailsService.update(userData), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping(value="userDetails/{id}", headers ="Accept=application/json")
	public ResponseEntity<UserRoleDetails> deleteUser(@PathVariable("id") Integer id){
		userRoleDetailsService.deleteUser(id);
		return new ResponseEntity<UserRoleDetails>(HttpStatus.NO_CONTENT);
	}
	
}