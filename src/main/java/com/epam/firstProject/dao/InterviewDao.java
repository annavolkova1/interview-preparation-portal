package com.epam.firstProject.dao;

import com.epam.firstProject.domain.Interview;
import java.sql.Timestamp;
import java.util.List;

public interface InterviewDao {

  List<Interview> getInterviewsOverThePeriod(Timestamp start, Timestamp end);

  List<Interview> getInterviewsByIntervieweeId(Long intervieweeId, Timestamp start, Timestamp end);

  List<Interview> getInterviewsByInterviewerId(Long interviewerId, Timestamp start, Timestamp end);

  void createInterview(Interview interview);

  boolean updateInterview(Interview interview);

  boolean deleteById(Long id);
}
