package org.happyrabbit.dao;

import org.apache.ibatis.annotations.Param;
import org.happyrabbit.entity.SuccessKilled;

public interface SuccessKilledDAO {


    /**
     * 插入成功购买明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    /**
     * 根据id获取SucessKilled商品
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWihtSeckill(@Param("seckillId") long seckillId,  @Param("userPhone") long userPhone);


}
