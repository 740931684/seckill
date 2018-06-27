package org.happyrabbit.service;


import org.happyrabbit.dto.Exposer;
import org.happyrabbit.dto.SeckillExcution;
import org.happyrabbit.entity.Seckill;
import org.happyrabbit.exception.RepeatKillException;
import org.happyrabbit.exception.SeckillCloseException;
import org.happyrabbit.exception.SeckillException;

import java.util.List;

/**
 * 业务接口设计：要站在使用者的角度来设计接口。注重外部封装
 * 1.方法定义粒度：合适
 * 2.参数：传入要简练
 * 3.返回类型：return类型要友好，注意封装。
 */
public interface SeckillSerivce {

    /**
     * 查询所有秒杀记录
     * @param
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     */
    Seckill getById(long seckillId);


    /**
     * 输入秒杀接口的地址
     * @param seckillId
     * 对秒杀接口进行加密，防止盗用
     */
    Exposer exportSeckillUrl(long seckillId);


    /**
     * 执行秒杀
     * @param  seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
    throws SeckillException,RepeatKillException,SeckillCloseException;
}
