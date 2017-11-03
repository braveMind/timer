package com.jun.timer.biz;


import com.jun.timer.common.RpcResponse;
import com.jun.timer.dto.JobParams;

import java.io.Closeable;

/**客户端执行  通过反射执行对应的定时任务
 * 服务端通过代理执行
 * Created by xunaiyang on 2017/7/6.
 */
public interface ExecutorBiz extends Closeable {
    public RpcResponse run(JobParams jobDto);
    public RpcResponse heartBeat();
}
