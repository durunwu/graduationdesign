package com.liujian.gymxmjpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description: 课程信息实体类
 * @Author: LiuJian
 * @Date: 2020/4/3
 */
@Entity
@Table(name = "user_reservation")
@Getter
@Setter
public class UserSubject {
  @Id
  @GeneratedValue(strategy =  GenerationType.IDENTITY)
  private long id;
  private long sub_id;
  private long admin_id;
  private String sub_name;
  private String reservation_start_time;
  private String reservation_end_time;
}
