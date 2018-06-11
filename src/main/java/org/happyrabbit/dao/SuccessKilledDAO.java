package org.happyrabbit.dao;

import org.happyrabbit.entity.SuccessKilled;

public interface SuccessKilledDAO {


    /**
     * 插入成功购买明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    int inserSuccessKilled(long seckillId,long userPhone);


    /**
     * 根据id获取SucessKilled商品
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWihtSeckill(long seckillId);


}
