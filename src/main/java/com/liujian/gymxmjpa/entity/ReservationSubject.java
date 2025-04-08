package com.liujian.gymxmjpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description: 课程信息实体类
 * @Author: LiuJian
 * @Date: 2020/4/3
 */
@Entity
@Table(name = "subject_reservation")
@Getter
@Setter
public class ReservationSubject {
  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long subId;
  private String subname;
  private double sellingPrice;
  private double subjectContainNum;
  private double alreadyReservationNum;
}
