package com.cts.fse.feedback.jwt.security;

import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.cts.fse.feedback.bean.UserRoleDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

    private String username;

    private int associateId;
   
    private String associateName;
   
    private String role;

    private String eventId;
    
    private String eventStatus;
    
    private String responded;
    
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(int associateId, String associateName, 
			    		String role,String eventId,String eventStatus,String responded) {
    	BCryptPasswordEncoder bcryptEncoder=new BCryptPasswordEncoder();
    	this.username=associateName;
    	this.password=bcryptEncoder.encode(String.valueOf(associateId));
        this.associateId=associateId;
        this.associateName=associateName;
        this.role=role;
        this.eventId=eventId;
        this.eventStatus=eventStatus;
        this.responded=responded;
    }

    public static UserPrinciple build(UserRoleDetails user) {
       
        return new UserPrinciple(
                user.getAssociateId(),
                user.getAssociateName(),
                user.getRole(),
                user.getEventId(),
                user.getEventStatus(),
                user.getResponded()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	@Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

	public int getAssociateId() {
		return associateId;
	}

	public void setAssociateId(int associateId) {
		this.associateId = associateId;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getResponded() {
		return responded;
	}

	public void setResponded(String responded) {
		this.responded = responded;
	}

	@Override
	public String toString() {
		return "UserPrinciple [associateId=" + associateId + ", associateName=" + associateName + ", role=" + role
				+ ", eventId=" + eventId + ", eventStatus=" + eventStatus + ", responded=" + responded + "]";
	}
    
    
}