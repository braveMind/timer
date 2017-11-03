package com.jun.timer.annotation;

import java.lang.annotation.*;

/**
 * Created by jun
 * 17/7/10 上午10:33.
 * des:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Clock {
    String name() default "";
    /*业务方法方法执行成功，如果业务端配置success方法，则回调无参success业务方法*/
    String success() default "";
    /*业务方法执行失败，如果业务端配置 fail方法，则回调fail无参业务方法*/
    String fail() default "";


}
