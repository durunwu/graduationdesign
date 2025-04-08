package com.liujian.gymxmjpa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
    private Integer subId;

    /**
     * 
     */
    private Long adminId;

    /**
     * 
     */
    private String subname;

    /**
     * 
     */
    private Date reservation_start_time;

    /**
     * 
     */
    private Date reservation_end_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserReservation other = (UserReservation) that;
        return (this.getSubId() == null ? other.getSubId() == null : this.getSubId().equals(other.getSubId()))
            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
            && (this.getSubname() == null ? other.getSubname() == null : this.getSubname().equals(other.getSubname()))
            && (this.getReservation_start_time() == null ? other.getReservation_start_time() == null : this.getReservation_start_time().equals(other.getReservation_start_time()))
            && (this.getReservation_end_time() == null ? other.getReservation_end_time() == null : this.getReservation_end_time().equals(other.getReservation_end_time()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubId() == null) ? 0 : getSubId().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getSubname() == null) ? 0 : getSubname().hashCode());
        result = prime * result + ((getReservation_start_time() == null) ? 0 : getReservation_start_time().hashCode());
        result = prime * result + ((getReservation_end_time() == null) ? 0 : getReservation_end_time().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subId=").append(subId);
        sb.append(", adminId=").append(adminId);
        sb.append(", subname=").append(subname);
        sb.append(", reservation_start_time=").append(reservation_start_time);
        sb.append(", reservation_end_time=").append(reservation_end_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}