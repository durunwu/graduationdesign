package com.xiaozhou.gymxmjpa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaozhou.gymxmjpa.entity.UserReservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author durun
* @description 针对表【user_reservation】的数据库操作Mapper
* @createDate 2025-04-09 00:32:11
* @Entity entity.com.xiaozhou.gymxmjpa.UserReservation
*/
@Mapper
public interface UserReservationMapper extends BaseMapper<UserReservation> {
    // 如果有自定义方法，确保其存在
    List<UserReservation> selectData(@Param("subname") String subname, @Param("adminId") long adminId);

    Integer selectDataPage(@Param("subname") String subname, @Param("adminId") long adminId, @Param("pageNumber") int pageNumber,@Param("pageSize") int pageSize);

}




