package com.herbert.seckill.dao;

import com.herbert.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Herbert on 2016/11/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id=1000L;
        long phone = 13278678888L;
        int insertCnt = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println(insertCnt);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled result = successKilledDao.queryByIdWithSeckill(1000L, 13278678888L);
        System.out.println(result.getSeckill());
        System.out.println(result);
    }
}