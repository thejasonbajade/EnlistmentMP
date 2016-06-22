package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

public class Semester {
 	public static Semester OPEN_SEMESTER = new Semester("Y2015_2ND");
 	private String semester;
 	
	public Semester(String semester) {
		notEmpty(semester);
		this.semester = semester;
	}
	
	public String semester() {
		return semester;
	}
	
	@Override
	public String toString() {
		return "Semester [semester=" + semester + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((semester == null) ? 0 : semester.hashCode());
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
		Semester other = (Semester) obj;
		if(semester == null) {
			if (other.semester != null)
				return false;
		} else if(!semester.equals(other.semester))
			return false;
		return true;
	}
 }