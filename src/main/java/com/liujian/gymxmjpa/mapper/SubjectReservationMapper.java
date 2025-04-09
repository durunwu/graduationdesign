package com.liujian.gymxmjpa.mapper;

import com.liujian.gymxmjpa.entity.SubjectReservation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author durun
* @description 针对表【subject_reservation】的数据库操作Mapper
* @createDate 2025-04-09 17:27:39
* @Entity com.liujian.gymxmjpa.entity.SubjectReservation
*/
@Mapper
public interface SubjectReservationMapper extends BaseMapper<SubjectReservation> {

    void updateNum(@Param("subId") Integer subId);

    Integer getMax(@Param("subId") Integer subId);

    Integer getAlreadyNum(@Param("subId") Integer subId);
}




