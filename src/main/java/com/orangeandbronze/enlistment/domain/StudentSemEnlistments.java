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

}
