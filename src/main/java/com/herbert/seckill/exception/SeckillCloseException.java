package com.herbert.seckill.exception;

/**
 * 秒杀关闭异常
 * Created by Herbert on 2016/11/13.
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
