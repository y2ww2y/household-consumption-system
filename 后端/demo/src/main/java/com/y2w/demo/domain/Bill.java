package com.y2w.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

/**
 *
 * @TableName bill
 */
@TableName(value ="bill")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bill implements Serializable {
    /**
     *
     */
    @TableId //默认雪花算法生成id
    private Long bid;

    private Date bdate;

    /**
     *
     */
    private String type;

    /**
     *
     */
    private Integer amout;

    /**
     *
     */
    private Integer consumerid;

    private String  descr;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
