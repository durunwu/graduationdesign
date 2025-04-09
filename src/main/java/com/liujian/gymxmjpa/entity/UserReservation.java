package com.liujian.gymxmjpa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName user_reservation
 */
@TableName(value ="user_reservation")
@Data
public class UserReservation implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @TableField("sub_id")
    private Integer subId;

    /**
     * 
     */
    @TableField("admin_id")
    private Integer adminId;

    /**
     * 
     */
    @TableField("sub_name")
    private String subName;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @TableField("reservation_start_time")
    private Date reservationStartTime;

    /**
     * 
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @TableField("reservation_end_time")
    private Date reservationEndTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}