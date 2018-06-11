package org.happyrabbit.dao;

import org.apache.ibatis.javassist.ClassPath;
import org.happyrabbit.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

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
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryById() {
    }

    @Test
    public void queryAll() {
    }
}