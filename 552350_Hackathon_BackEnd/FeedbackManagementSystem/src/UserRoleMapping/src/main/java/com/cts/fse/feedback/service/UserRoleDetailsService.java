package com.cts.fse.feedback.service;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import com.cts.fse.feedback.bean.UserRoleDetails;

public interface UserRoleDetailsService extends UserDetailsService {

	public UserRoleDetails createUser(UserRoleDetails user);
	public Iterable<UserRoleDetails> getUser();
	public UserRoleDetails findById(Integer associateId);
	public UserRoleDetails update(UserRoleDetails user);
	public void deleteUser(Integer id);
	public void uploadUserRoleDetails(MultipartFile file) throws IOException;
	
}
