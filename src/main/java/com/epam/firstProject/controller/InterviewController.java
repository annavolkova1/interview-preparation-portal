package com.epam.firstProject.controller;

import com.epam.firstProject.connection.ThymeleafServlet;
import com.epam.firstProject.domain.Interview;
import com.epam.firstProject.domain.ScheduledTimeslot;
import com.epam.firstProject.domain.User;
import com.epam.firstProject.service.InterviewService;
import com.epam.firstProject.service.TimeslotService;
import com.epam.firstProject.service.implementation.InterviewServiceImpl;
import com.epam.firstProject.service.implementation.TimeslotServiceImpl;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InterviewController extends ThymeleafServlet {

  private final Interview interview = new Interview();
  private final InterviewService interviewService = new InterviewServiceImpl();
  private final TimeslotService timeslotService = new TimeslotServiceImpl();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession(true);

    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String desc = request.getParameter("desc");
    String timeslotId = request.getParameter("id");
    User skillUser = (User) session.getAttribute("user");
    Long skillId = skillUser.getUserSkills().get(0).getId();

    List<ScheduledTimeslot> scheduleTimeslotById = timeslotService.getScheduleTimeslotById(Long.parseLong(timeslotId));

    ScheduledTimeslot scheduledTimeslot = scheduleTimeslotById.get(0);
    if (!scheduledTimeslot.isActive()) {
      Long interviewerId = scheduledTimeslot.getInterviewerId();
      Long intervieweeId = skillUser.getId();

      interview.setComment(desc);
      interview.setSkillId(skillId);
      interview.setInterviewerId(interviewerId);
      interview.setIntervieweeId(intervieweeId);
      interview.setStartTime(new Timestamp(Long.parseLong(startTime)));
      interview.setEndTime(new Timestamp(Long.parseLong(endTime)));

      interviewService.createInterview(interview);

      scheduledTimeslot.setActive(true);
      timeslotService.updateScheduledTimeslot(scheduledTimeslot);
    }
    response.sendRedirect("schedule");
  }
}
