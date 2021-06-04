package com.epam.firstProject.dao;

import com.epam.firstProject.domain.User;
import java.util.List;

public interface UserDao {

  List<User> getUsers();

  boolean createUser(User user);

  boolean updateUser(User user);

  User getByEmail(String email);
}
