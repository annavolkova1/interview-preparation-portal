package com.epam.firstProject.controller;

import com.epam.firstProject.connection.ConnectionProvider;
import com.epam.firstProject.connection.ThymeleafServlet;
import com.epam.firstProject.domain.ScheduledTimeslot;
import com.epam.firstProject.domain.User;
import com.epam.firstProject.service.TimeslotService;
import com.epam.firstProject.service.implementation.TimeslotServiceImpl;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.thymeleaf.context.WebContext;

public class ScheduleController extends ThymeleafServlet {

  static final Log logger = LogFactory.getLog(ConnectionProvider.class);

  private final TimeslotService timeslotService = new TimeslotServiceImpl();

  private final ScheduledTimeslot scheduledTimeslot = new ScheduledTimeslot();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    super.doGet(request, response);

    WebContext webContext = new WebContext(request, response, getServletConfig().getServletContext(),
        request.getLocale());

    Timestamp start = new Timestamp(1612137600000L);
    Timestamp end = new Timestamp(1641028240000L);

    List<ScheduledTimeslot> events = timeslotService.getScheduledTimeslotsOverThePeriod(start, end);

    ScheduledTimeslot[] scheduledTimeslots = events.toArray(new ScheduledTimeslot[0]);
    DisplayEvent[] displayEvents = new DisplayEvent[events.size()];

    for (int i = 0; i < scheduledTimeslots.length; i++) {
      String title = scheduledTimeslots[i].getComment();
      String eventStart = String.valueOf(scheduledTimeslots[i].getStartTime());
      String eventEnd = String.valueOf(scheduledTimeslots[i].getEndTime());
      String id = String.valueOf(scheduledTimeslots[i].getId());
      String activeSlot = String.valueOf(scheduledTimeslots[i].isActive());

      displayEvents[i] = new DisplayEvent(title, eventStart, eventEnd);

      displayEvents[i].setId(id);
      displayEvents[i].setTitle(title);
      displayEvents[i].setStart(eventStart);
      displayEvents[i].setEnd(eventEnd);
      displayEvents[i].setActive(activeSlot);
      if (scheduledTimeslots[i].isActive()) {
        displayEvents[i].setColor("#06ccbc");
      }
    }
    webContext.setVariable("events", displayEvents);
    getTemplateEngine().process("schedule", webContext, response.getWriter());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    WebContext webContext = new WebContext(request, response, getServletConfig().getServletContext(),
        request.getLocale());

    HttpSession session = request.getSession();

    String id = request.getParameter("id");
    if (id == null) {
      String startTime = request.getParameter("startTime");
      String endTime = request.getParameter("endTime");
      String desc = request.getParameter("desc");
      User skillUser = (User) session.getAttribute("user");
      Long skillId = skillUser.getUserSkills().get(0).getId();

      Long interviewerId = skillUser.getId();

      scheduledTimeslot.setComment(desc);
      scheduledTimeslot.setInterviewerId(interviewerId);
      scheduledTimeslot.setSkillId(skillId);
      scheduledTimeslot.setActive(false);

      try {
        scheduledTimeslot.setStartTime(Timestamp.valueOf(convertToTimeStampFormat(startTime)));
        scheduledTimeslot.setEndTime(Timestamp.valueOf(convertToTimeStampFormat(endTime)));

        Timestamp scheduledTimeslotStartTime = scheduledTimeslot.getStartTime();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (currentTime.compareTo(scheduledTimeslotStartTime) < 0) {

          timeslotService.createScheduledTimeslot(scheduledTimeslot);
        }
        else {
          logger.error("You are trying to create a time slot in the past!");
          getTemplateEngine().process("invalidDate", webContext, response.getWriter());
        }
      }
      catch (ParseException e) {
        logger.error(e);
      }
    }
    else {
      Long idEvent = Long.parseLong(request.getParameter("id"));
      timeslotService.deleteById(idEvent);
    }
    doGet(request, response);
  }

  public static String convertToTimeStampFormat(String dateStr) throws ParseException {

    TimeZone utc = TimeZone.getTimeZone("UTC");
    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sourceFormat.setTimeZone(utc);
    Date convertedDate = sourceFormat.parse(dateStr);
    return destFormat.format(convertedDate);
  }
}
