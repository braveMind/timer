package com.jun.timer.handler;

/**
 * Created by jun
 * 17/7/5 下午1:47.
 * des:
 */
public interface Handler<T1,T2>{
    T1 handle(T2 t2);
}
