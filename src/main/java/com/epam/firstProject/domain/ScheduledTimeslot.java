package com.epam.firstProject.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class ScheduledTimeslot {

  private Long id;
  private Long skillId;
  private Long interviewerId;
  private Timestamp startTime;
  private Timestamp endTime;
  private String comment;
  private boolean active;

  public ScheduledTimeslot() {

  }

  public ScheduledTimeslot(Long id, Long skillId, Long interviewerId, Timestamp startTime, Timestamp endTime,
      String comment, boolean active) {

    this.id = id;
    this.skillId = skillId;
    this.interviewerId = interviewerId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.comment = comment;
    this.active = active;
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

  public boolean isActive() {

    return active;
  }

  public void setActive(boolean active) {

    this.active = active;
  }

  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }
    if (object == null || ScheduledTimeslot.class != object.getClass()) {
      return false;
    }
    ScheduledTimeslot scheduledTimeslot = (ScheduledTimeslot) object;
    return Objects.equals(id, scheduledTimeslot.id) && Objects.equals(skillId, scheduledTimeslot.skillId)
        && Objects.equals(interviewerId, scheduledTimeslot.interviewerId)
        && Objects.equals(startTime, scheduledTimeslot.startTime) && Objects.equals(endTime, scheduledTimeslot.endTime)
        && Objects.equals(comment, scheduledTimeslot.comment) && Objects.equals(active, scheduledTimeslot.active);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, skillId, interviewerId, startTime, endTime, comment, active);
  }

  @Override
  public String toString() {

    return "ScheduledTimeslot{" +
        "id=" + id +
        ", skillId=" + skillId +
        ", interviewerId=" + interviewerId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", comment='" + comment + '\'' +
        ", active=" + active +
        '}';
  }
}
