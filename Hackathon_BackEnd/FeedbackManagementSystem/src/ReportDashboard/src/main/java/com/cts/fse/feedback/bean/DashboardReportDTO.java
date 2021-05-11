package com.cts.fse.feedback.bean;

import java.math.BigDecimal;

public class DashboardReportDTO {

	private String eventId;
	private int participatedCount;
	private int notAttendedCount;
	private int unRegisteredCount;
	private BigDecimal averageSmileyCount;
	
	
	
	public BigDecimal getAverageSmileyCount() {
		return averageSmileyCount;
	}
	public void setAverageSmileyCount(BigDecimal averageSmileyCount) {
		this.averageSmileyCount = averageSmileyCount;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public int getParticipatedCount() {
		return participatedCount;
	}
	public void setParticipatedCount(int participatedCount) {
		this.participatedCount = participatedCount;
	}
	public int getNotAttendedCount() {
		return notAttendedCount;
	}
	public void setNotAttendedCount(int notAttendedCount) {
		this.notAttendedCount = notAttendedCount;
	}
	public int getUnRegisteredCount() {
		return unRegisteredCount;
	}
	public void setUnRegisteredCount(int unRegisteredCount) {
		this.unRegisteredCount = unRegisteredCount;
	}
	@Override
	public String toString() {
		return "DashboardReportDTO [eventId=" + eventId + ", participatedCount=" + participatedCount
				+ ", notAttendedCount=" + notAttendedCount + ", unRegisteredCount=" + unRegisteredCount + "]";
	}


	
	
}
