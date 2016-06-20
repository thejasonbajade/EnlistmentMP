package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

public class EnlistService {
	
	private SectionDAO sectionDao;
	private StudentDAO studentDao;
	private StudentSemEnlistmentsDAO studentSemEnlistmentsDao;
	
	public void enlist(int studentNo, String sectionId) {
		// TODO: Implement this method
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
