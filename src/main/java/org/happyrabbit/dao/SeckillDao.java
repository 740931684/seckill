package org.happyrabbit.dao;

import org.apache.ibatis.annotations.Param;
import org.happyrabbit.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killtime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 通过商品id获取秒杀产品
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 通过偏移量获取产品
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offset, int limit);
}
