package com.orangeandbronze.enlistment.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class Student {
	private final int studentNo;
	private Collection<StudentSemEnlistments> enlistmentsPerSem = new HashSet<StudentSemEnlistments>();
	
	public Student(int studentNo) {
		this(studentNo, new LinkedList<StudentSemEnlistments>());
	}
	
	public Student(int studentNo, LinkedList<StudentSemEnlistments> allSectionsPerSem) {
		if(studentNo < 0) 
			throw new IllegalArgumentException("Student number was negative. " + this);
		this.studentNo = studentNo;
		this.enlistmentsPerSem.addAll(allSectionsPerSem);
	}
	
	public StudentSemEnlistments enlist(Section section) {
		StudentSemEnlistments studentSemEnlistments = getCurrentSemEnlistments();
		studentSemEnlistments.enlist(section);
		return studentSemEnlistments;
	}
	
	public StudentSemEnlistments getCurrentSemEnlistments() {
		for(StudentSemEnlistments sectionsInASem : enlistmentsPerSem) {
			if(sectionsInASem.isOpenSemester())
				return sectionsInASem;
		}
		StudentSemEnlistments currentSem = new StudentSemEnlistments(this, Semester.OPEN_SEMESTER);
		enlistmentsPerSem.add(currentSem);
		return currentSem;
	}
	
	public int getStudentNo() {
		return studentNo;
	}
	
	public void setEnlistmentsPerSem(Collection<StudentSemEnlistments> allSections) {
		this.enlistmentsPerSem = new HashSet<>(allSections);
	}
	
	public Collection<StudentSemEnlistments> getEnlistmentsPerSem() {
		return new HashSet<>(enlistmentsPerSem);
	}
	
	@Override
	public String toString() {
		return "Student [studentNo=" + studentNo + ". " + enlistmentsPerSem + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentNo;
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
		Student other = (Student) obj;
		if(studentNo != other.studentNo)
			return false;
		return true;
	}
	
}
