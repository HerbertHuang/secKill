package com.herbert.seckill.service.impl;

import com.herbert.seckill.dao.SecKillDao;
import com.herbert.seckill.dao.SuccessKilledDao;
import com.herbert.seckill.dto.Exposer;
import com.herbert.seckill.dto.SeckillExecution;
import com.herbert.seckill.entity.Seckill;
import com.herbert.seckill.entity.SuccessKilled;
import com.herbert.seckill.enums.SeckillStateEnum;
import com.herbert.seckill.exception.RepeatKillException;
import com.herbert.seckill.exception.SeckillCloseException;
import com.herbert.seckill.exception.SeckillException;
import com.herbert.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Herbert on 2016/11/13.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String salt = "qrwqpaoidif0930w**0q0q";

    @Autowired
    private SecKillDao secKillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    public List<Seckill> getSeckillList() {
        return secKillDao.queryAll(0, 100);
    }

    public Seckill getById(long seckillId) {
        return secKillDao.queryById(seckillId);
    }

    /**
     * 秒杀开启输出秒杀地址
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = secKillDao.queryById(seckillId);
        if(seckill == null){
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();
        if(now.getTime()<startTime.getTime() ||
                now.getTime()>endTime.getTime()){
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(),
                    endTime.getTime());
        }

        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 执行秒杀操作
     *使用注解控制事务的优点:
     * 1:开发团队达成一致的约定,明确表明事务方法的编程风格
     * 2:保证事务方法的执行时间尽可能短,不要穿拆其他网络操作RPC/HTTP请求或者剥离到事务方法外
     * 3:不是所有的方法都需要事务,如只有一条修改操作,只读操作是不需要事务控制的.
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, SeckillCloseException, RepeatKillException {
        if(md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑
        Date nowTime = new Date();
        try {
            int updateCount = secKillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0){
                throw new SeckillCloseException("seckill is close");
            } else {
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if(insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //将所有异常归类为运行期异常,方便spring事务rollback
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }
}
