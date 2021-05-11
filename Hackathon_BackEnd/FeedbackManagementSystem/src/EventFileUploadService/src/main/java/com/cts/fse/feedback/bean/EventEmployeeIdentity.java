package com.cts.fse.feedback.bean;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class EventEmployeeIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
    private Integer associateId;
	
	@NotNull
    private String eventId;

	public Integer getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Integer associateId) {
		this.associateId = associateId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associateId == null) ? 0 : associateId.hashCode());
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventEmployeeIdentity other = (EventEmployeeIdentity) obj;
		if (associateId == null) {
			if (other.associateId != null)
				return false;
		} else if (!associateId.equals(other.associateId))
			return false;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EventEmployeeIdentity [associateId=" + associateId + ", eventId=" + eventId + "]";
	}
	 
	 
}
