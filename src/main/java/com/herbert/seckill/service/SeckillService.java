package com.herbert.seckill.service;

import com.herbert.seckill.dto.Exposer;
import com.herbert.seckill.dto.SeckillExecution;
import com.herbert.seckill.entity.Seckill;
import com.herbert.seckill.exception.RepeatKillException;
import com.herbert.seckill.exception.SeckillCloseException;
import com.herbert.seckill.exception.SeckillException;

import java.util.List;

/**
 * 很重要的业务接口设计思路
 * 1-方法定义的粒度(暴露多少个方法),参数(个数/类型),返回类型(return 类型和异常)
 * Created by Herbert on 2016/11/13.
 */
public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    /**
     * 秒杀开启输出秒杀地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, SeckillCloseException, RepeatKillException;
}
