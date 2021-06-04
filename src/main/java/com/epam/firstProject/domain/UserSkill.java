package com.epam.firstProject.domain;

import com.epam.firstProject.dao.enums.SkillName;
import java.util.Objects;

public class UserSkill {

  private long id;
  private long userId;
  private SkillName skillName;

  public UserSkill() {

  }

  public UserSkill(long userId, SkillName skillName) {

    this.userId = userId;
    this.skillName = skillName;
  }

  public UserSkill(long id, long userId, SkillName skillName) {

    this.id = id;
    this.userId = userId;
    this.skillName = skillName;
  }

  public long getUserId() {

    return userId;
  }

  public void setUserId(long userId) {

    this.userId = userId;
  }

  public long getId() {

    return id;
  }

  public void setId(long id) {

    this.id = id;
  }

  public SkillName getSkillName() {

    return skillName;
  }

  public void setSkillName(SkillName skillName) {

    this.skillName = skillName;
  }

  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }
    if (object == null || UserSkill.class != object.getClass()) {
      return false;
    }
    UserSkill userSkill = (UserSkill) object;
    return id == userSkill.id
        && userId == userSkill.userId
        && Objects.equals(skillName, userSkill.skillName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, userId, skillName);
  }

  @Override
  public String toString() {

    return "UserSkill{" +
        "id=" + id +
        ", userId=" + userId +
        ", skillName='" + skillName + '\'' +
        '}';
  }
}
