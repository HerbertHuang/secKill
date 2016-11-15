package com.herbert.seckill.dao;

import com.herbert.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Herbert on 2016/11/13.
 */

/**
 * 配置spring和junit的整合,junit启动时加载springIOC容器
 * spring-test, junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void queryById() throws Exception {
        long id=1000;
        Seckill seckill = secKillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        int offset = 0;
        int limit = 10;
        List<Seckill> seckillList = secKillDao.queryAll(offset, limit);
        for (Seckill seckill:seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() throws Exception {
        int result = secKillDao.reduceNumber((long)1000L, new Date());
        System.out.println("reduce number is"+result);
    }

}