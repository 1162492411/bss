package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Area implements Serializable,BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Update.class, Delete.class})
    private Integer id;
    /**
     * 区域名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;
    /**
     * 区域最北部
     */
    @TableField("north_point")
    @NotNull(groups = {Insert.class, Update.class})
    @Range(min = -180, max = 180, groups = {Insert.class, Update.class})
    private BigDecimal northPoint;
    /**
     * 区域最南部
     */
    @TableField("south_point")
    @NotNull(groups = {Insert.class, Update.class})
    @Range(min = -180, max = 180, groups = {Insert.class, Update.class})
    private BigDecimal southPoint;
    /**
     * 区域最西部
     */
    @TableField("west_point")
    @NotNull(groups = {Insert.class, Update.class})
    @Range(min = -180, max = 180, groups = {Insert.class, Update.class})
    private BigDecimal westPoint;
    /**
     * 区域最东部
     */
    @TableField("east_point")
    @NotNull(groups = {Insert.class, Update.class})
    @Range(min = -180, max = 180, groups = {Insert.class, Update.class})
    private BigDecimal eastPoint;
    /**
     * 区域类型 : 见AreaTypeEnum
     */
    @NotNull(groups = {Insert.class, Update.class})
    @Range(min = 1 ,max = 3, groups = {Insert.class, Update.class} , message = "区域类型设置错误")
    private Integer type;

}
