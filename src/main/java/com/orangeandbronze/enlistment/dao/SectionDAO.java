package com.orangeandbronze.enlistment.dao;

import com.orangeandbronze.enlistment.domain.Section;

public interface SectionDAO {

	Section findBy(String sectionId);
	
}
