package com.epam.firstProject.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Interview {

  private Long id;
  private Long skillId;
  private Long intervieweeId;
  private Long interviewerId;
  private Timestamp startTime;
  private Timestamp endTime;
  private String comment;

  public Interview() {

  }

  public Interview(Long id, Long skillId, Long intervieweeId, Long interviewerId, Timestamp startTime,
      Timestamp endTime,
      String comment) {

    this.id = id;
    this.skillId = skillId;
    this.intervieweeId = intervieweeId;
    this.interviewerId = interviewerId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
  }

  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }
    if (object == null || Interview.class != object.getClass()) {
      return false;
    }
    Interview interview = (Interview) object;
    return Objects.equals(id, interview.id) && Objects.equals(skillId, interview.skillId)
        && Objects.equals(intervieweeId, interview.intervieweeId)
        && Objects.equals(interviewerId, interview.interviewerId)
        && Objects.equals(startTime, interview.startTime) && Objects.equals(endTime, interview.endTime)
        && Objects.equals(comment, interview.comment);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, skillId, intervieweeId, interviewerId, startTime, endTime, comment);
  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {

    this.id = id;
  }

  public Long getSkillId() {

    return skillId;
  }

  public void setSkillId(Long skillId) {

    this.skillId = skillId;
  }

  public Long getIntervieweeId() {

    return intervieweeId;
  }

  public void setIntervieweeId(Long intervieweeId) {

    this.intervieweeId = intervieweeId;
  }

  public Long getInterviewerId() {

    return interviewerId;
  }

  public void setInterviewerId(Long interviewerId) {

    this.interviewerId = interviewerId;
  }

  public Timestamp getStartTime() {

    return startTime;
  }

  public void setStartTime(Timestamp startTime) {

    this.startTime = startTime;
  }

  public Timestamp getEndTime() {

    return endTime;
  }

  public void setEndTime(Timestamp endTime) {

    this.endTime = endTime;
  }

  public String getComment() {

    return comment;
  }

  public void setComment(String comment) {

    this.comment = comment;
  }
}
