package com.xiaozhou.gymxmjpa.entity;

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
public class Subject {
  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long subId;
  private String subname;
  private Double sellingPrice;
  private Double subjectContainNum;
 /* @JoinColumn
  private List<PrivateCoachInfo> privateCoachInfos;*/
// @OneToMany
// @JoinColumn(name = "subjectid")
//  private PrivateCoachInfo privateCoachInfo;

  /*

  @OneToMany
  @JsonIgnore
  private List<PrivateCoach> privateCoachList;*/

}
