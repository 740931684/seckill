package org.happyrabbit.service;

import org.happyrabbit.dto.Exposer;
import org.happyrabbit.dto.SeckillExcution;
import org.happyrabbit.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillSerivceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillSerivce seckillSerivce;

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillSerivce.getSeckillList();
        for (Seckill seckill : list) {
            logger.info("seckill="+seckill.toString());
        }

    }

    @Test
    public void getById() {
        long id = 1000;
        Seckill seckill = seckillSerivce.getById(id);
        logger.info("seckill = " + seckill.toString());
    }

    @Test
    public void exportSeckillUrl() {
        long id = 1000;
        Exposer exposer = seckillSerivce.exportSeckillUrl(id);

        logger.info("exposer = {}",exposer.toString());

    }

    @Test
    public void excuteSeckill() {

        long id = 1000;
        long userPhone = 12200003333L;
        String md5 = "aa62040536c1f0e3a037ad0bb53ce837";

        SeckillExcution seckillExcution = seckillSerivce.excuteSeckill(id,userPhone,md5);

        logger.info("seckillExcution = {}",seckillExcution.toString());
    }
}