package com.epam.firstProject.dao;

import com.epam.firstProject.domain.UserSkill;
import java.util.List;

public interface UserSkillDao {

  List<UserSkill> getUserSkills(long id);

  boolean addUserSkill(UserSkill userSkill);

  boolean delete(long id);
}
