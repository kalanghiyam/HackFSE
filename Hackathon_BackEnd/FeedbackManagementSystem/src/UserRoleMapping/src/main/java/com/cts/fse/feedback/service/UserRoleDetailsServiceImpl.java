package com.cts.fse.feedback.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.cts.fse.feedback.bean.UserRoleDetails;
import com.cts.fse.feedback.jwt.security.UserPrinciple;
import com.cts.fse.feedback.repository.UserRoleDetailsRepository;
import com.cts.fse.feedback.utils.Utils;



@Service
@Transactional
public class UserRoleDetailsServiceImpl implements UserRoleDetailsService {

	
	@Autowired
	private UserRoleDetailsRepository  userRoleDetailsRepository;
	

	@Autowired
	private RestTemplate restTemplate;
  
	@Value("${login.url}")
	private String loginUrl;

	@Override
	public UserRoleDetails createUser(UserRoleDetails saveUser) {
		UserRoleDetails user =new  UserRoleDetails();
		user.setAssociateId(saveUser.getAssociateId());
		user.setAssociateName(saveUser.getAssociateName());
		user.setRole(saveUser.getRole());
		return userRoleDetailsRepository.save(user);
	}

	@Override
	public Iterable<UserRoleDetails> getUser() {
		return userRoleDetailsRepository.findAll();
	}

	@Override
	public UserRoleDetails findById(Integer associateId) {
		List<UserRoleDetails> userRoleDetails = userRoleDetailsRepository.getUserById(associateId);
		return userRoleDetails!=null ? userRoleDetails.get(0) : null;
	}

	@Override
	public UserRoleDetails update(UserRoleDetails user) {
		return userRoleDetailsRepository.save(user);
	}

	@Override
	public void deleteUser(Integer id) {
	  userRoleDetailsRepository.deleteById(id);
	}

	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("------------------------------>");
		Optional<UserRoleDetails> userDetails = userRoleDetailsRepository.findById(Integer.parseInt(username));
		UserRoleDetails user=null;
		
		
		 if(!userDetails.isPresent() ) {
			 user = restTemplate.getForObject(loginUrl+username, UserRoleDetails.class);
			 if(user==null) {
				 new UsernameNotFoundException("User Not Found with -> username or email : " + username);
			 }
		 }else {
			 user = userDetails.orElseThrow(
						() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		 }
		
		return UserPrinciple.build(user);
	}
	
	@Override
	public void uploadUserRoleDetails(MultipartFile file) throws IOException {
			  FileInputStream inputStream=(FileInputStream) file.getInputStream();
			  List<UserRoleDetails>  userRoleDetails= Utils.parseExcelFileToBeans(inputStream,"userRole.xml");
			  userRoleDetailsRepository.saveAll(userRoleDetails);
	}
	
}