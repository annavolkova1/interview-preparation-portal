package com.epam.firstProject.service.implementation;

import com.epam.firstProject.dao.implementation.UserDaoImpl;
import com.epam.firstProject.dao.implementation.UserRoleDaoImpl;
import com.epam.firstProject.dao.implementation.UserSkillDaoImpl;
import com.epam.firstProject.domain.User;
import com.epam.firstProject.domain.UserRole;
import com.epam.firstProject.domain.UserSkill;
import com.epam.firstProject.service.UserService;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserServiceImpl implements UserService {

  private final static Log logger = LogFactory.getLog(UserSkillDaoImpl.class);
  private UserRoleDaoImpl userRoleDaoImpl = new UserRoleDaoImpl();
  private UserDaoImpl userDaoImpl = new UserDaoImpl();
  private UserSkillDaoImpl userSkillDaoImpl = new UserSkillDaoImpl();

  public UserServiceImpl() {

  }

  public static String encryptPassword(String password) {

    String hashFunctionName = "SHA-256";
    String toReturn = null;
    try {
      MessageDigest digest = MessageDigest.getInstance(hashFunctionName);
      digest.reset();
      digest.update(password.getBytes(StandardCharsets.UTF_8));
      toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
    }
    catch (NoSuchAlgorithmException e) {
      logger.error(e);
    }

    return toReturn;
  }

  @Override
  public boolean isPasswordCorrect(String password, String email) {

    String passwordEncrypted = encryptPassword(password);
    User userReceived = getByEmail(email);
    String passwordFound = userReceived.getPassword();
    if (passwordEncrypted.equals(passwordFound)) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public List<User> getUsers() {

    List<User> users = userDaoImpl.getUsers();
    return users;
  }

  @Override
  public User getByEmail(String email) {

    User user = null;
    try {
      user = userDaoImpl.getByEmail(email);
      long id = user.getId();
      List<UserRole> userRoles = userRoleDaoImpl.getUserRoles(id);
      List<UserSkill> userSkills = userSkillDaoImpl.getUserSkills(id);
      user.setUserRolesById(userRoles);
      user.setUserSkillsById(userSkills);
      return user;
    }
    catch (NullPointerException throwable) {
      logger.error(throwable);
    }
    return null;
  }

  @Override
  public boolean create(User user) {

    String password = user.getPassword();
    String passwordEncrypted = encryptPassword(password);
    user.setPassword(passwordEncrypted);
    userDaoImpl.createUser(user);
    return true;
  }

  @Override
  public boolean addSkill(UserSkill userSkill) {

    userSkillDaoImpl.addUserSkill(userSkill);
    return false;
  }
}
