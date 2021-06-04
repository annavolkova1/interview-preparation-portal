package com.epam.firstProject.service;

import com.epam.firstProject.domain.User;
import com.epam.firstProject.domain.UserSkill;
import java.util.List;

public interface UserService {

  User getByEmail(String email);

  boolean create(User user);

  boolean addSkill(UserSkill userSkill);

  List<User> getUsers();

  boolean isPasswordCorrect(String password, String email);
}
