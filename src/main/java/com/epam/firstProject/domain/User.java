package com.epam.firstProject.domain;

import java.util.List;
import java.util.Objects;

public class User {

  private long id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private List<UserRole> userRoles;
  private List<UserSkill> userSkills;

  public User() {

  }

  public User(String email, String password, String firstName, String lastName) {

    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User(String email, String firstName, String lastName,
      List<UserRole> userRoles, List<UserSkill> userSkills) {

    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userRoles = userRoles;
    this.userSkills = userSkills;
  }

  public User(long id, String email, String password, String firstName, String lastName,
      List<UserRole> userRolesById, List<UserSkill> userSkillsById) {

    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userRoles = userRolesById;
    this.userSkills = userSkillsById;
  }

  public User(long id, String email, String password, String firstName, String lastName) {

    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User(long id, String email, String firstName, String lastName,
      List<UserRole> userRoles, List<UserSkill> userSkills) {

    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userRoles = userRoles;
    this.userSkills = userSkills;
  }

  public long getId() {

    return id;
  }

  public void setId(long id) {

    this.id = id;
  }

  public String getEmail() {

    return email;
  }

  public void setEmail(String email) {

    this.email = email;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  public String getFirstName() {

    return firstName;
  }

  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  public String getLastName() {

    return lastName;
  }

  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  public List<UserRole> getUserRolesById() {

    return userRoles;
  }

  public void setUserRolesById(List<UserRole> userRolesById) {

    this.userRoles = userRolesById;
  }

  public List<UserSkill> getUserSkillsById() {

    return userSkills;
  }

  public void setUserSkillsById(List<UserSkill> userSkillsById) {

    this.userSkills = userSkillsById;
  }

  public List<UserRole> getUserRoles() {

    return userRoles;
  }

  public void setUserRoles(List<UserRole> userRoles) {

    this.userRoles = userRoles;
  }

  public List<UserSkill> getUserSkills() {

    return userSkills;
  }

  public void setUserSkills(List<UserSkill> userSkills) {

    this.userSkills = userSkills;
  }

  @Override
  public String toString() {

    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", userRolesById=" + userSkills + '\'' +
        ", userRolesById=" + userRoles +
        '}';
  }

  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }
    if (object == null || User.class != object.getClass()) {
      return false;
    }
    User user = (User) object;
    return id == user.id &&
        Objects.equals(email, user.email)
        && Objects.equals(password, user.password)
        && Objects.equals(firstName, user.firstName)
        && Objects.equals(lastName, user.lastName)
        && Objects.equals(userRoles, user.userRoles)
        && Objects.equals(userSkills, user.userSkills);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, email, password, firstName, lastName, userRoles, userSkills);
  }
}
