package com.orangeandbronze.enlistment.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.orangeandbronze.enlistment.dao.StudentDAO;

public class StudentTest {
private StudentDAO studentDao = mock(StudentDAO.class);
	
	@Test
	public void theSameStudent() {	
		
		final int studentNo = 123;
		Student actualStudent = new Student(studentNo);
		when(studentDao.findBy(studentNo)).thenReturn(actualStudent);
			
		assertEquals(studentNo, actualStudent.getStudentNo());
		assertEquals(new Student(123), actualStudent);
	}
	
	@Test
	public void negativeStudentNumber() {
		try {
			final int studentNo = -123;
			Student actualStudent = new Student(studentNo);
			when(studentDao.findBy(studentNo)).thenReturn(actualStudent);		
		} catch(IllegalArgumentException e) {
			assertTrue(e.getMessage().startsWith("Student number was negative."));
			return;
		}
		fail("Should throw IllegalArgumentException");
	}
	
	@Test
	public void testIfStudentIsEnlistedInASubject() {
		final int studentNo = 123;
		Student actualStudent = new Student(studentNo);
		//assertTrue();
	}
}
