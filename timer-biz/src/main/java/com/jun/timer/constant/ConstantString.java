package com.jun.timer.constant;

/**
 * Created by xunaiyang on 2017/7/16.
 */
public class ConstantString {
    //没接入配置中心租户暂时写死
    public static final String tenant= "1";


    public static final String LOG_TEMPLATE = "应用名：{0} \n任务名：{1} \n执行类：{2} \n执行方法：{3} \n执行参数：{4} \n调度结果：{5} ";

    public static final String APP_MESSAGE_TEMPLATE = "您的应用[{0}]所对应的机器已全部下线，您的任务[{1}]将无法被调度，请及时检查应用状态！！！";

    public static final String JOB_MESSAGE_TEMPLATE = "您的任务[{0}]调度失败，请及时检查应用状态！！！";

    public static String REDIS_CATEGORY = "zhaopin-timer";

    /*redis 对象存活时间 默认5分钟*/
    public static int STORE_TIME = 500;

    /*应用最大不活跃时间，超过此时间应用将被摘除*/
    public static long CHECK_TIME = 300l * 1000l;

    public static final String LION_KEY_PUBLIC_ID = "zhaopin-app-leopard.sendXMPublicId"; //大象公众号
}
