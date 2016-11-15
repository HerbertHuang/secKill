package com.herbert.seckill.service;

import com.herbert.seckill.dto.Exposer;
import com.herbert.seckill.dto.SeckillExecution;
import com.herbert.seckill.entity.Seckill;
import com.herbert.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Herbert on 2016/11/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("list={}", seckillList);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill{}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer{}", exposer);
        //exposerExposer{exposed=true,
        // md5='3f4937ca77bf3498eb931820f8639c61',
        // seckillId=1000, now=0, start=0, end=0}
    }

    @Test
    public void executeSeckill() throws Exception {
        long seckillId = 1001;
        long phonenum = 13211112228L;
        String md5="3f4937ca77bf3498eb931820f8639c61";
        SeckillExecution result = null;
        try {
            result = seckillService.executeSeckill(seckillId, phonenum, md5);
            logger.info("result{}", result);
        } catch (SeckillException e) {
            logger.info(e.getMessage());
        }


    }
}