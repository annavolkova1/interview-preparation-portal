package com.epam.firstProject.dao.enums;

public enum SkillName {
  JAVA(1),
  JAVASCRIPT(2);

  private long id;

  SkillName(long id) {

    this.id = id;
  }

  public static SkillName getById(long id) {

    for (SkillName skillName : SkillName.values()) {
      if (id == skillName.getId()) {
        return skillName;
      }
    }
    throw new IllegalArgumentException("No skill found for this user id");
  }

  public long getId() {

    return id;
  }
}
