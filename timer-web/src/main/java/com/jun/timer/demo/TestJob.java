package com.jun.timer.demo;

import com.jun.timer.annotation.Clock;
import com.jun.timer.annotation.Timer;
import org.springframework.stereotype.Service;

/**
 * @author jun
 * @Date 18/6/1 .
 * @des:
 */
@Service
@Timer("testJob")
public class TestJob {

    /**
     * 周期性任务
     * @param value
     */
    @Clock
    public void testPrint(String value){
        System.out.println(value);
    }
}
