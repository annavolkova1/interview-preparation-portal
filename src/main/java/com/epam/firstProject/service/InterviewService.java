package com.epam.firstProject.service;

import com.epam.firstProject.domain.Interview;
import java.sql.Timestamp;
import java.util.List;

public interface InterviewService {

  List<Interview> getInterviewsOverThePeriod(Timestamp start, Timestamp end);

  void createInterview(Interview interview);

  boolean deleteById(Long id);
}
