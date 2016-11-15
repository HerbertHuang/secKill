package com.herbert.seckill.exception;

/**
 * 重复秒杀异常
 * Created by Herbert on 2016/11/13.
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
