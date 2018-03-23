package com.zhd.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 行政区划表
 * </p>
 *
 * @author zyg
 * @since 2018-03-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable,BaseModel{

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 区域级别
     */
    private Integer level;

    /**
     * 邮政编码
     */
    private Integer code;

    /**
     * 父类ID
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 中心位置X
     */
    @TableField("center_x")
    private BigDecimal centerX;

    /**
     * 中心位置Y
     */
    @TableField("center_y")
    private BigDecimal centerY;

}
