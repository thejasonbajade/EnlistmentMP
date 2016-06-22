package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;
import static org.apache.commons.lang3.Validate.*;

public class EnlistService {
	
	private SectionDAO sectionDao;
	private StudentDAO studentDao;
	private StudentSemEnlistmentsDAO studentSemEnlistmentsDao;
	
	public void enlist(int studentNo, String sectionId) {
		if(studentNo < 0) {
			throw new IllegalArgumentException(
					"Student number was negative. Student [studentNo=" + studentNo + "]");
		}
		notEmpty(sectionId);
		Student student = studentDao.findBy(studentNo);
		Section section = sectionDao.findBy(sectionId);
		
		StudentSemEnlistments studentSemEnlistments = student.enlist(section);
		studentSemEnlistmentsDao.update(studentSemEnlistments);
	
	}

	public void setSectionDao(SectionDAO sectionDao) {
		this.sectionDao = sectionDao;
	}

	public void setStudentDao(StudentDAO studentDao) {
		this.studentDao = studentDao;
	}

	public void setStudentSemEnlistmentsDao(
		StudentSemEnlistmentsDAO studentSemEnlistmentsDao) {
		this.studentSemEnlistmentsDao = studentSemEnlistmentsDao;
	}
}
