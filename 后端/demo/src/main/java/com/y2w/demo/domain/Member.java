package com.y2w.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;

/**
 *
 * @TableName member
 */
@TableName(value ="member")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member implements Serializable {
    /**
     *
     */
    @TableId(type=IdType.AUTO)//id自增,数据库也要设置
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String passward;


    private Integer role;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
