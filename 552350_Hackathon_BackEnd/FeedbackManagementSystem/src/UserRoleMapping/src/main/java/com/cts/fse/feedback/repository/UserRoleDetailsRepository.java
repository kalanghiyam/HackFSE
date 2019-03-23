package com.cts.fse.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cts.fse.feedback.bean.UserRoleDetails;

@Repository
public interface UserRoleDetailsRepository extends CrudRepository<UserRoleDetails, Integer>
{
	@Query("select user from UserRoleDetails user where user.associateId=:associateId")
	List<UserRoleDetails> getUserById(@Param("associateId") int associateId);
	
	
}
