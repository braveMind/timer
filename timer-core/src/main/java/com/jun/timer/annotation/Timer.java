package com.jun.timer.annotation;

import java.lang.annotation.*;

/**
 * Created by jun
 * 17/7/5 下午3:56.
 * des:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Timer {
    /**
     * 类名字
     * @return
     */
    String value() default "";

}
