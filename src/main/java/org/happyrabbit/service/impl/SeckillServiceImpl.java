package org.happyrabbit.service.impl;

import org.happyrabbit.dao.SeckillDao;
import org.happyrabbit.dao.SuccessKilledDAO;
import org.happyrabbit.dto.Exposer;
import org.happyrabbit.dto.SeckillExcution;
import org.happyrabbit.entity.Seckill;
import org.happyrabbit.entity.SuccessKilled;
import org.happyrabbit.enums.SeckillStateEnum;
import org.happyrabbit.exception.RepeatKillException;
import org.happyrabbit.exception.SeckillCloseException;
import org.happyrabbit.exception.SeckillException;
import org.happyrabbit.service.SeckillSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 业务接口实现类
 */
//@Component：统称的一个组件注解  @Service：如果确定是一个service，则使用。  @Dao  @Controller

@Service
public class SeckillServiceImpl implements SeckillSerivce {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入Service依赖
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDAO successKilledDAO;

    //用于混淆md5
    private final String slat = "sdfas0r29r2))";

    /**
     * 查询所有秒杀记录
     */
    @Override
    public List<Seckill> getSeckillList() {

        return seckillDao.queryAll(0,4);
    }

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     */
    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 输入秒杀接口的地址
     *
     * @param seckillId 对秒杀接口进行加密，防止盗用
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        Date nowTime = new Date();

        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {

            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //MD5的值
        String md5 = getMD5(seckillId);

        return new Exposer(true,md5,seckillId);
    }

    /**
     * 执行秒杀
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    @Override
    @Transactional
    public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {

        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("您的操作不合法！invalid operation!");
        }

        Date nowTime = new Date();

        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

            if (updateCount <= 0) {
                //没有更新到记录
                throw new SeckillCloseException("seckill is closed");
            } else {
                //插入秒杀明细
                int insertCount = successKilledDAO.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //userPhone此电话号码重复秒杀了seckillId对应的产品
                    throw new RepeatKillException("您已经秒杀了该产品，请勿重复秒杀！");
                } else {
                    SuccessKilled successKilled = successKilledDAO.queryByIdWihtSeckill(seckillId, userPhone);
                    return new SeckillExcution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {

            //捕获所有编译期异常，转化成运行期异常，告诉spring声明式实物，进行实物回滚
            throw new SeckillException("Seckill inner error! " + e.getMessage());
        }



    }


    //生成MD5
    private String getMD5(long secillId) {

        String base = secillId + "/" +slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }

}
