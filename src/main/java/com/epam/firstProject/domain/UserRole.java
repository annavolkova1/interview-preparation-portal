package com.epam.firstProject.domain;

import com.epam.firstProject.dao.enums.RoleName;

public class UserRole {

  private long id;
  private RoleName roleName;
  private long userId;

  public UserRole() {

  }

  public UserRole(RoleName roleName, long userId) {

    this.roleName = roleName;
    this.userId = userId;
  }

  public UserRole(long id, RoleName roleName, long userId) {

    this.id = id;
    this.roleName = roleName;
    this.userId = userId;
  }

  public long getId() {

    return id;
  }

  public void setId(long id) {

    this.id = id;
  }

  public RoleName getRoleName() {

    return roleName;
  }

  public void setRoleName(RoleName roleName) {

    this.roleName = roleName;
  }

  public long getUserId() {

    return userId;
  }

  public void setUserId(long userId) {

    this.userId = userId;
  }

  @Override
  public String toString() {

    return "UserRole{" +
        "id=" + id +
        ", userRole='" + roleName + '\'' +
        ", userId=" + userId +
        '}';
  }
}



