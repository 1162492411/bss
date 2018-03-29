package com.zhd.mapper;

import com.zhd.pojo.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.annotation.security.PermitAll;
import java.math.BigDecimal;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zyg
 * @since 2018-02-05
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("update user set account_balance = account_balance + #{amount} where id = #{uid}")
    Integer rechargeAccount(@Param("uid") String id, @Param("amount") BigDecimal amount);

    @Update("update user set account_balance = account_balance - #{amount} where id = #{id}")
    Integer reduceAccount(@Param("id")String id, @Param("amount") BigDecimal amount);

    @Update("update user set deposit_balance = deposit_balance + #{amount} where id = #{uid}")
    Integer rechargeDeposit(@Param("uid")String id, @Param("amount") BigDecimal amount);

    @Update("update user set deposit_balance = deposit_balance - #{amount} where id = #{uid}")
    Integer refundDeposit(@Param("uid")String id, @Param("amount") BigDecimal amount);

    @Update("update user set credit = credit + 1 where id = #{id}")
    Integer increaseCredit(@Param("id")String id);

    @Update("update user set credit = credit - 10 where id = #{id}")
    Integer reduceCredit(@Param("id")String id);

}
