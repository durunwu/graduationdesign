package com.liujian.gymxmjpa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName subject_reservation
 */
@TableName(value ="subject_reservation")
@Data
public class SubjectReservation implements Serializable {
    /**
     * 
     */
    @TableId(value = "subId", type = IdType.AUTO)
    private Integer subId;

    /**
     * 
     */
    @TableField(value = "subname")
    private String subname;

    /**
     * 
     */
    @TableField(value = "sellingPrice")
    private Double sellingPrice;

    /**
     * 
     */
    @TableField(value = "subjectContainNum")
    private Double subjectContainNum;

    /**
     * 
     */
    @TableField(value = "alreadyReservationNum")
    private Double alreadyReservationNum;

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
        SubjectReservation other = (SubjectReservation) that;
        return (this.getSubId() == null ? other.getSubId() == null : this.getSubId().equals(other.getSubId()))
            && (this.getSubname() == null ? other.getSubname() == null : this.getSubname().equals(other.getSubname()))
            && (this.getSellingPrice() == null ? other.getSellingPrice() == null : this.getSellingPrice().equals(other.getSellingPrice()))
            && (this.getSubjectContainNum() == null ? other.getSubjectContainNum() == null : this.getSubjectContainNum().equals(other.getSubjectContainNum()))
            && (this.getAlreadyReservationNum() == null ? other.getAlreadyReservationNum() == null : this.getAlreadyReservationNum().equals(other.getAlreadyReservationNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSubId() == null) ? 0 : getSubId().hashCode());
        result = prime * result + ((getSubname() == null) ? 0 : getSubname().hashCode());
        result = prime * result + ((getSellingPrice() == null) ? 0 : getSellingPrice().hashCode());
        result = prime * result + ((getSubjectContainNum() == null) ? 0 : getSubjectContainNum().hashCode());
        result = prime * result + ((getAlreadyReservationNum() == null) ? 0 : getAlreadyReservationNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subId=").append(subId);
        sb.append(", subname=").append(subname);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", subjectContainNum=").append(subjectContainNum);
        sb.append(", alreadyReservationNum=").append(alreadyReservationNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}