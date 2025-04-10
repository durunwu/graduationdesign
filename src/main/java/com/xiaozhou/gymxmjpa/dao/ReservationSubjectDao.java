package com.xiaozhou.gymxmjpa.dao;


import com.xiaozhou.gymxmjpa.entity.ReservationSubject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: 课程信息Dao层接口
 * @Author: LiuJian
 * @Date: 2020/4/3
 */
public interface ReservationSubjectDao extends JpaRepository<ReservationSubject,Long> {
}
