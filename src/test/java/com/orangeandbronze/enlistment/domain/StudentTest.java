package com.orangeandbronze.enlistment.domain;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import org.junit.Test;
import static com.orangeandbronze.enlistment.domain.Schedule.Day.*;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.*;

public class StudentTest {
	
	@Test
	public void theSameStudent() {			
		final int studentNo = 123;
		Student actualStudent = new Student(studentNo);
			
		assertEquals(studentNo, actualStudent.getStudentNo());
		assertEquals(new Student(123), actualStudent);
	}
	
	@Test
	public void negativeStudentNumber() {
		try {
			new Student(-123);	
		} catch(IllegalArgumentException e) {
			return;
		}
		fail("Should throw IllegalArgumentException");
	}
	
	@Test
	public void enlistedOneSubject() {
		Student student = new Student(123);
		Section section = new Section("Sec1", new Subject("CMSC11"), new Room("Rm206", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		student.enlist(section);
		assertTrue(student.getCurrentSemEnlistments().getSections().contains(section));
	}
	
	@Test
	public void enlistWithScheduleConflict() {
		Student student = new Student(123);
		Section firstSection = new Section("Sec1", new Subject("CMSC11"), new Room("Rm206", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		Section secondSection = new Section("Sec1", new Subject("CMSC21"), new Room("Rm207", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		try {
			student.enlist(firstSection);
			student.enlist(secondSection);
		} catch(ScheduleConflictException e) {
			assertEquals(firstSection.getSchedule(), secondSection.getSchedule());
			return;
		}
		fail("Should throw ScheduleConflictException");
	}
	
	@Test
	public void enlistWithoutScheduleConflict() {
		Student student = new Student(123);
		Section firstSection = new Section("Sec1", new Subject("CMSC11"), new Room("Rm206", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		Section secondSection = new Section("Sec1", new Subject("CMSC21"), new Room("Rm207", 50), new Schedule(WED_SAT, EIGHT_THIRTY_AM));
		
		student.enlist(firstSection);
		student.enlist(secondSection);
		
		assertTrue(!firstSection.getSchedule().equals(secondSection.getSchedule()));
	}
	
	@Test
	public void enlistThatExceedsRoomCapacity() {
		Student student = new Student(123);
		Room room = new Room("Rm206", 50);
		room.setCurrentCapacity(50);
		Section section = new Section("Sec1", new Subject("CMSC11"), room, new Schedule(MON_THU, EIGHT_THIRTY_AM));
		try {			
			student.enlist(section);		
		} catch(ExceedingRoomCapacityException e) {
			assertEquals(50, room.getCurrentCapacity());
			return;
		}
		fail("Should throw ExceedingRoomCapacityException"); 
	}
	@Test
	public void enlistThatExceedsRoomCapacityWithTwoDifferentStudentsAndTheLastCannotEnlist() {
		Student firstStudent = new Student(123);
		Student secondStudent = new Student(456);
		Room room = new Room("Rm206", 50);
		room.setCurrentCapacity(49);
		Section section = new Section("Sec1", new Subject("CMSC11"), room, new Schedule(MON_THU, EIGHT_THIRTY_AM));
		try {			
			firstStudent.enlist(section);
			secondStudent.enlist(section);
		} catch(ExceedingRoomCapacityException e) {
			assertEquals(50, room.getCurrentCapacity());
			assertTrue(!secondStudent.getCurrentSemEnlistments().getSections().contains(section));
			return;
		}
		fail("Should throw ExceedingRoomCapacityException"); 
	}
	
	@Test
	public void enlistToSectionWithTheSameSubject() {
		Student student = new Student(123);
		Section firstSection = new Section("Sec1", new Subject("CMSC11"), new Room("Rm206", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		Section secondSection = new Section("Sec1", new Subject("CMSC11"), new Room("Rm207", 50), new Schedule(WED_SAT, EIGHT_THIRTY_AM));
		try {
			student.enlist(firstSection);
			student.enlist(secondSection);
		} catch(SimilarSubjectException e) {
			assertEquals(firstSection.getSubject(), secondSection.getSubject());
			return;
		}
	}
	
	@Test
	public void enlistWhereStudentHasNotTakenThePrerequisite() {
		Student student = new Student(123);
		Section firstSection = new Section("Sec1", new Subject("CMSC1"), new Room("Rm206", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		StudentSemEnlistments semEnlistments = new StudentSemEnlistments(student, new Semester("Y2015_1ST"));
		Collection<StudentSemEnlistments> allSections = new HashSet<StudentSemEnlistments>();
		
		semEnlistments.addSection(firstSection);
		allSections.add(semEnlistments);
		student.setEnlistmentsPerSem(allSections);
		
		Section secondSection = new Section("Sec1", new Subject("CMSC21", new Subject("CMSC11")), new Room("Rm207", 50), new Schedule(WED_SAT, EIGHT_THIRTY_AM));
		try {
			student.enlist(secondSection);
		} catch(PrerequisiteNotTakenException e) {
			assertTrue(!firstSection.getSubject().equals(secondSection.getSubject().getPrerequisite()));
			return;
		}
		fail("Should throw PrerequisiteNotTakenException");
	}
	
	@Test
	public void enlistWhereStudentHasTakenThePrerequisite() {
		Student student = new Student(123);
		Section firstSection = new Section("Sec1", new Subject("CMSC11"), new Room("Rm206", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		StudentSemEnlistments semEnlistments = new StudentSemEnlistments(student, new Semester("Y2015_1ST"));
		Collection<StudentSemEnlistments> allSections = new HashSet<StudentSemEnlistments>();
		
		semEnlistments.addSection(firstSection);
		allSections.add(semEnlistments);
		student.setEnlistmentsPerSem(allSections);
		
		Section secondSection = new Section("Sec1", new Subject("CMSC21", new Subject("CMSC11")), new Room("Rm207", 50), new Schedule(WED_SAT, EIGHT_THIRTY_AM));
		
		student.enlist(secondSection);
		assertTrue(firstSection.getSubject().equals(secondSection.getSubject().getPrerequisite()));
	}
}