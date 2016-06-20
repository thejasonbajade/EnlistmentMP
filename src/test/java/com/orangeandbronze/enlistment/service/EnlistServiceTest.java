package com.orangeandbronze.enlistment.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.*;
import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class EnlistServiceTest {
	// TODO: Refactor EnlistServiceTest
	
	private SectionDAO sectionDao = mock(SectionDAO.class); 
	private StudentDAO studentDao = mock(StudentDAO.class);
	private StudentSemEnlistmentsDAO studentSemEnlistmentsDao = mock(StudentSemEnlistmentsDAO.class);
	
	private EnlistService enlistService  = new EnlistService();
	
	@Before
	public void initialize() {
		enlistService.setSectionDao(sectionDao);
		enlistService.setStudentDao(studentDao);
		enlistService.setStudentSemEnlistmentsDao(studentSemEnlistmentsDao);
	}

	@Test
	public void enlistWithoutConflict() {	
		final int studentNo = 123;
		Student student = new Student(studentNo);
		when(studentDao.findBy(studentNo)).thenReturn(student);
		
		final String subjectId = "Java101";
		Subject subject = new Subject(subjectId, null);
		
		final String roomName = "Room123";
		final int maxCapacity = 20;
		Room room = new Room(roomName, maxCapacity);
		room.setCurrentCapacity(19);
		
		Schedule schedule = new Schedule(Schedule.Days.MON_THU, Schedule.Periods.FIRST);
		Semester semester = new Semester("SecondSemester");
		
		final String sectionId = "123ABC";
		Section section = new Section(sectionId, subject, room, schedule, semester);
		when(sectionDao.findBy(sectionId)).thenReturn(section);	
		
		//StudentSemEnlistments studentSemEnlistments = new StudentSemEnlistments(student, semester);
		//when(studentSemEnlistmentsDao.update(studentSemEnlistments)).thenReturn(studentSemEnlistments);
		Semester openSemester = new Semester("SecondSemester");
		
		enlistService.enlist(studentNo, sectionId);
		
		assertEquals(section.getSemester(), openSemester);
		assertFalse(room.getCurrentCapacity() > room.getMaxCapacity());
		assertEquals(20, room.getCurrentCapacity());
		assertEquals(studentNo, student.getStudentNo());
		assertEquals(sectionId, section.getSectionId());
	}
	
	@Test
	public void enlistWithScheduleConflict() {
		final int studentNo = 1;
		Student student = new Student(studentNo);
		when(studentDao.findBy(studentNo)).thenReturn(student);
		
		final String subjectId = "Java101";
		Subject subject = new Subject(subjectId, null);
		
		final String roomName = "Room123";
		final int maxCapacity = 20;
		Room room = new Room(roomName, maxCapacity);
		room.setCurrentCapacity(19);
		
		Schedule schedule = new Schedule(Schedule.Days.MON_THU, Schedule.Periods.FIRST);
		Semester semester = new Semester("SecondSemester");
		
		final String firstSectionId = "123ABC";
		Section firstSection = new Section(firstSectionId, subject, room, schedule, semester);
		when(sectionDao.findBy(firstSectionId)).thenReturn(firstSection);
		
		final String secondSectionId = "456XYZ";
		Section secondSection = new Section(secondSectionId, subject, room, schedule, semester);
		when(sectionDao.findBy(secondSectionId)).thenReturn(secondSection);
		
		Semester semester = new Semester("FirstSem");
		
		try {
			enlistService.enlist(studentNo, firstSectionId);
			enlistService.enlist(studentNo, secondSectionId);
		} catch(ScheduleConflictException e) {
			assertEquals(firstSection.getSchedule(), secondSection.getSchedule());
			assertTrue(e.getMessage().startsWith("Section cannot have the same schedule."));
			return;
		}
		fail("Should throw ScheduleConflictException");
	}
	
	@Test
	public void enlistThatExceedsRoomCapacity() {
		final int studentNo = 1;
		Student student = new Student(studentNo);
		when(studentDao.findBy(studentNo)).thenReturn(student);
		
		final String subjectId = "Java101";
		Subject subject = new Subject(subjectId, null);
		
		Schedule schedule = new Schedule(Schedule.Days.MON_THU, Schedule.Periods.FIRST);
		
		final String roomName = "Room123";
		final int maxCapacity = 20;
		Room room = new Room(roomName, maxCapacity);
		room.setCurrentCapacity(20);
		
		Semester semester = new Semester("SecondSemester");
		
		final String sectionId = "123ABC";
		Section section = new Section(sectionId, subject, room, schedule, semester);
		when(sectionDao.findBy(sectionId)).thenReturn(section);
		
		try {			
			enlistService.enlist(studentNo, sectionId);
			
		} catch(ExceedingRoomCapacityException e) {
			assertEquals(20, room.getCurrentCapacity());
			assertTrue(e.getMessage().startsWith("Student"));
			return;
		}
		fail("Should throw ExceedingRoomCapacityException"); 
	}
	
	@Test
	public void enlistSectionWithTheSameSubject() {
		final int studentNo = 1;
		Student student = new Student(studentNo);
		when(studentDao.findBy(studentNo)).thenReturn(student);
		
		final String subjectId = "Java101";
		Subject subject = new Subject(subjectId, null);
		
		final String roomName = "Room123";
		final int maxCapacity = 20;
		Room room = new Room(roomName, maxCapacity);
		room.setCurrentCapacity(10);
		
		Schedule schedule = new Schedule(Schedule.Days.MON_THU, Schedule.Periods.FIRST);
		
		Semester semester = new Semester("SecondSemester");
		
		final String firstSectionId = "123ABC";
		Section firstSection = new Section(firstSectionId, subject, room, schedule, semester);
		when(sectionDao.findBy(firstSectionId)).thenReturn(firstSection);
		
		final String secondSectionId = "456XYZ";
		Section secondSection = new Section(secondSectionId, subject, room, schedule, semester);
		when(sectionDao.findBy(secondSectionId)).thenReturn(secondSection);
		try {	
			//StudentSemEnlistments studentSemEnlistments = new StudentSemEnlistments(student, semester);
			//when(studentSemEnlistmentsDao.update(studentSemEnlistments)).thenReturn(studentSemEnlistments);
			
			enlistService.enlist(studentNo, firstSectionId);
			enlistService.enlist(studentNo, secondSectionId);
		} catch(SimilarSubjectException e) {
			
			assertEquals(firstSection.getSubject(), secondSection.getSubject());
			assertTrue(e.getMessage().startsWith("Subject for enlisted section already taken."));
			return;
		}
		fail("Should throw SimilarSubjectException");
	}
	
	@Test
	public void enlistInADifferentSemester() {
		final int studentNo = 1;
		Student student = new Student(studentNo);
		when(studentDao.findBy(studentNo)).thenReturn(student);
		
		final String subjectId = "Java101";
		Subject subject = new Subject(subjectId, null);
		
		final String roomName = "Room123";
		final int maxCapacity = 20;
		Room room = new Room(roomName, maxCapacity);
		room.setCurrentCapacity(10);
		
		Schedule schedule = new Schedule(Schedule.Days.MON_THU, Schedule.Periods.FIRST);
		Semester semester = new Semester("SecondSemester");
		
		final String firstSectionId = "123ABC";
		Section firstSection = new Section(firstSectionId, subject, room, schedule, semester);
		when(sectionDao.findBy(firstSectionId)).thenReturn(firstSection);
		
		Semester openSemester = new Semester("SecondSemester");
		try {
			enlistService.enlist(studentNo, firstSectionId);
		} catch(IncorrectSemesterException e) {
			assertTrue(firstSection.getSemester().equals(openSemester));
			return;
		}
		fail("Should throw IncorrectSemesterException");
	}
	
	@Test
	public void enlistWhereStudentHasNotTakenThePrerequisite() {
		try {
			
		} catch(PrerequisiteNotTakenException e) {
			//assertEquals();
			return;
		}
		fail("Should throw PrerequisiteNotTakenException");
	}
}
