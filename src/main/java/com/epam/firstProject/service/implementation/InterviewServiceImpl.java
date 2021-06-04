package com.epam.firstProject.service.implementation;

import com.epam.firstProject.dao.implementation.InterviewDaoImpl;
import com.epam.firstProject.domain.Interview;
import com.epam.firstProject.service.InterviewService;
import java.sql.Timestamp;
import java.util.List;

public class InterviewServiceImpl implements InterviewService {

  private final InterviewDaoImpl interviewDao = new InterviewDaoImpl();

  @Override
  public List<Interview> getInterviewsOverThePeriod(Timestamp start, Timestamp end) {

    return interviewDao.getInterviewsOverThePeriod(start, end);
  }

  @Override
  public void createInterview(Interview interview) {

    interviewDao.createInterview(interview);
  }

  @Override
  public boolean deleteById(Long id) {

    return interviewDao.deleteById(id);
  }
}
