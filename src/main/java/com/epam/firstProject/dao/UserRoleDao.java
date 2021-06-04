package com.epam.firstProject.dao;

import com.epam.firstProject.domain.UserRole;
import java.util.List;

public interface UserRoleDao {

  List<UserRole> getUserRoles(long id);

  boolean addUserRole(UserRole userRole);

  boolean updateUserRole(UserRole userRole);
}
