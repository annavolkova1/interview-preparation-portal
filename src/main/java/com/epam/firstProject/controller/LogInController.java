package com.epam.firstProject.controller;

import com.epam.firstProject.connection.ThymeleafServlet;
import com.epam.firstProject.domain.User;
import com.epam.firstProject.domain.UserRole;
import com.epam.firstProject.domain.UserSkill;
import com.epam.firstProject.service.UserService;
import com.epam.firstProject.service.implementation.UserServiceImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.thymeleaf.context.WebContext;

public class LogInController extends ThymeleafServlet {

  UserService userService = new UserServiceImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    super.doGet(request, response);

    WebContext webContext = new WebContext(request, response, getServletConfig().getServletContext(),
        request.getLocale());
    getTemplateEngine().process("login", webContext, response.getWriter());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    WebContext webContext =
        new WebContext(request, response, getServletConfig().getServletContext(), request.getLocale());
    String email = request.getParameter("email");
    String passwordReceived = request.getParameter("password");
    User userEntered = userService.getByEmail(email);

    long id = userEntered.getId();
    String lastName = userEntered.getLastName();
    String firstName = userEntered.getFirstName();
    List<UserRole> userRoles = userEntered.getUserRolesById();
    List<UserSkill> userSkills = userEntered.getUserSkillsById();
    User userForSession = new User(id, email, firstName, lastName, userRoles, userSkills);

    if (userService.isPasswordCorrect(passwordReceived, email)) {

      HttpSession oldSession = request.getSession(false);
      if (oldSession != null) {
        oldSession.invalidate();
      }
      HttpSession session = request.getSession();
      session.setAttribute("user", userForSession);
      System.out.println("suc if");
      getTemplateEngine().process("loginsuccess", webContext, response.getWriter());
    }
    else {
      System.out.println("suc else");

      getTemplateEngine().process("loginerror", webContext, response.getWriter());
    }
  }
}
