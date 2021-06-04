package com.epam.firstProject.controller;

import com.epam.firstProject.connection.ThymeleafServlet;
import com.epam.firstProject.dao.enums.SkillName;
import com.epam.firstProject.domain.User;
import com.epam.firstProject.domain.UserSkill;
import com.epam.firstProject.service.UserService;
import com.epam.firstProject.service.implementation.UserServiceImpl;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

public class RegistrationController extends ThymeleafServlet {

  UserService userService = new UserServiceImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    super.doGet(request, response);

    WebContext webContext = new WebContext(request, response, getServletConfig().getServletContext(),
        request.getLocale());
    List<SkillName> skills = Arrays.asList(SkillName.values());
    webContext.setVariable("skills", skills);
    getTemplateEngine().process("registration", webContext, response.getWriter());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    User user = new User();
    UserSkill userSkill = new UserSkill();
    WebContext webContext =
        new WebContext(request, response, getServletConfig().getServletContext(), request.getLocale());
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String firstName = request.getParameter("first_name");
    String lastName = request.getParameter("last_name");
    long id = Integer.parseInt(request.getParameter("skill_id"));
    SkillName skillNameById = SkillName.getById(id);
    user.setEmail(email);
    user.setPassword(password);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    userSkill.setSkillName(skillNameById);
    User userByEmail = userService.getByEmail(email);

    if (userByEmail == null) {
      getTemplateEngine().process("success", webContext, response.getWriter());
      userService.create(user);
      User userCreated = userService.getByEmail(email);
      long createdUserId = userCreated.getId();
      userSkill.setUserId(createdUserId);
      userService.addSkill(userSkill);
    }
    else {
      getTemplateEngine().process("failure", webContext, response.getWriter());
    }
  }
}
