package com.orangeandbronze.enlistment.dao;

import com.orangeandbronze.enlistment.domain.Student;

public interface StudentDAO {
	
	Student findBy(int id);
	
}
