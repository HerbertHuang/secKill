package com.herbert.seckill.dao;

import com.herbert.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Herbert on 2016/10/27.
 */
public interface SecKillDao {

    public int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime")Date killTime);

    public Seckill queryById(long seckillId);

    public List<Seckill> queryAll(int offset, int limit);

    public void killByProcedure(Map<String, Object> paramMap);

    public void aa();
}
