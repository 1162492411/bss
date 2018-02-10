package com.zhd.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable, BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(groups = {Update.class,Delete.class})
    private Integer id;
    /**
     * 供应商名
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;

    /**
     * 供应商地址
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String address;

}
