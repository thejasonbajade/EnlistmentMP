package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

import java.util.*;

public class StudentSemEnlistments {
	private final Student student;
	private final Semester semester;
	private final Collection<Section> sections = new HashSet<>();

	public StudentSemEnlistments(Student student, Semester semester) {
		notNull(student);
		notNull(semester);
		this.student = student;
		this.semester = semester;
	}
	
	public void addSection(Section section) {
		sections.add(section);
	}
	
	public boolean isOpenSemester() {
		return semester.equals(Semester.OPEN_SEMESTER);
	}
	
	public void enlist(Section section) {
		checkSemConflict();
		checkScheduleConflict(section);
		checkRoomConflict(section);
		checkPrerequisite(section);
		sections.add(section);
		section.getRoom().incrementCurrentCapacity();
	}
	
	public void checkScheduleConflict(Section section) {
		section.checkScheduleConflict(sections);
	}
	
	public void checkSemConflict() {
		if(!isOpenSemester()) {
			throw new IncorrectSemesterException("Not allowed to enroll in a semester not equal to open semester" + this);
		} 
	}
	
	public void checkRoomConflict(Section section){
		section.checkRoomConflict();
	}
	
	public void checkPrerequisite(Section section) {
		section.checkPrerequisite(student.getEnlistmentsPerSem());
	}
	
	public Student getStudent() {
		return student;
	}
	
	public Semester getSemester() {
		return semester;
	}

	public Collection<Section> getSections() {
		return new HashSet<>(sections);
	}
	
	@Override
	public String toString() {
		return "StudentSemEnlistments [student=" + student + ", semester=" 
						+ semester + ", sections=" + sections + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((semester == null) ? 0 : semester.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		StudentSemEnlistments other = (StudentSemEnlistments) obj;
		if (semester == null) {
			if (other.semester != null)
				return false;
		} else if (!semester.equals(other.semester))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}
}
