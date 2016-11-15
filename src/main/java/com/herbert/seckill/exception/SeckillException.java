package com.herbert.seckill.exception;

/**
 * Created by Herbert on 2016/11/13.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillException(String message) {
        super(message);
    }
}
