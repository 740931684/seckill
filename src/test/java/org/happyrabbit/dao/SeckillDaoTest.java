package org.happyrabbit.dao;

import org.happyrabbit.entity.Seckill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合，junit启动时加载springIOC
 * spring-test，junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {

        Date today = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式时间对象
        try {
            today = sdf.parse("2018-06-06 12:00:00");
            int reduceNum = seckillDao.reduceNumber(1000L, today);
            System.out.println("=====================================");


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void queryById() {

        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println("=====================================");

        System.out.println(seckill.getName());
        System.out.println(seckill);
        Assert.assertEquals(seckill.getName(), "1000元秒杀iphone7");
    }

    @Test
    public void queryAll() {

        List<Seckill> seckills = seckillDao.queryAll(0,100);

        for (Seckill seckill : seckills) {
            System.out.println(seckill.getName());
        }
    }
}