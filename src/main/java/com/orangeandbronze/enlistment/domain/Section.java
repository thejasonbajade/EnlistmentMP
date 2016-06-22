package com.orangeandbronze.enlistment.domain;

import java.util.Collection;
import static org.apache.commons.lang3.Validate.*;

public class Section {
	
	private final String sectionId;
	private final Subject subject;
	private final Room room;
	private final Schedule schedule;
	
	public Section(String sectionId, Subject subject, Room room, Schedule schedule) {
		notEmpty(sectionId);
		notNull(subject);
		notNull(room);
		notNull(schedule);
		this.sectionId = sectionId;
		this.subject = subject;
		this.room = room;
		this.schedule = schedule;
	}
	
	public String getSectionId() {
		return sectionId;
	}
	
	public Subject getSubject() {
		return subject;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
	
	public void incrementRoomCapacity() {
		room.incrementCurrentCapacity();
	}
	
	public void checkScheduleConflict(Collection<Section> enlistedSections) {
		schedule.checkScheduleConflict(enlistedSections);
	}
	
	public void checkSubjectConflict(Collection<Section> enlistedSections) {
		subject.checkSubjectConflict(enlistedSections);
	}
	
	public void checkRoomConflict() {
		room.checkRoomConflict();
	}
	
	public void checkPrerequisite(Collection<StudentSemEnlistments> allSections) {
		subject.checkPrerequisite(allSections);
	}
	
	@Override
	public String toString() {
		return "Section [sectionId=" + sectionId + ", subject" + subject
				+ ", room=" + room + ", schedule=" + schedule + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		if(sectionId == null) {
			if(other.sectionId != null)
				return false;
		} else if(!sectionId.equals(other.sectionId))
			return false;
		return true;
	}	
}
