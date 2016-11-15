package com.herbert.seckill.dao;

import com.herbert.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;
/**
 * Created by Herbert on 2016/11/13.
 */
public interface SuccessKilledDao {

    /**
     * 插入秒杀成功数量
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone") long userPhone);
}
