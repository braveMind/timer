package com.jun.timer.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xunaiyang on 2017/7/24.
 */
@Component
public class DefaultThreadPool {
    public static final ExecutorService executor= Executors.newFixedThreadPool(8);
}
