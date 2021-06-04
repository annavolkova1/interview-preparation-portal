package com.epam.firstProject.connection;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class ThymeleafServlet extends HttpServlet {

  private static final TemplateEngine templateEngine;

  static {

    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    templateResolver.setTemplateMode("XHTML");
    templateResolver.setPrefix("/WEB-INF/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCacheTTLMs(3600000L);
    templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
  }

  public static TemplateEngine getTemplateEngine() {

    return templateEngine;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
  }
}
