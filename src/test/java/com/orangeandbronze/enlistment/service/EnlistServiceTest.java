package com.orangeandbronze.enlistment.service;

import static com.orangeandbronze.enlistment.domain.Schedule.Day.*;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class EnlistServiceTest {
	
	@Test
	public void testEnlistService() {
		SectionDAO sectionDao = mock(SectionDAO.class); 
		StudentDAO studentDao = mock(StudentDAO.class);
		StudentSemEnlistmentsDAO studentSemEnlistmentsDao = mock(StudentSemEnlistmentsDAO.class);
		
		EnlistService enlistService  = new EnlistService();
		
		enlistService.setSectionDao(sectionDao);
		enlistService.setStudentDao(studentDao);
		enlistService.setStudentSemEnlistmentsDao(studentSemEnlistmentsDao);
		
		Student student = new Student(123);
		Section section = new Section("Sec1", new Subject("CMSC11"), new Room("Rm206", 50), new Schedule(MON_THU, EIGHT_THIRTY_AM));
		
		when(studentDao.findBy(123)).thenReturn(student);
		when(sectionDao.findBy("Sec1")).thenReturn(section);
		
		enlistService.enlist(123, "Sec1");
		
		StudentSemEnlistments semEnlistments = new StudentSemEnlistments(student, Semester.OPEN_SEMESTER);
		semEnlistments.addSection(section);

		verify(studentSemEnlistmentsDao).update(semEnlistments);
		assertTrue(student.getCurrentSemEnlistments().getSections().contains(section));
	}
}
