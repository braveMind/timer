package com.jun.timer.application;

import com.jun.timer.dto.EmailMessageDto;
import com.jun.timer.dto.JobDto;

/**
 * Created by xunaiyang on 2017/7/24.
 */
public interface MessageApplication {

    void sendAlarmMessage(EmailMessageDto emailMessageDto);
}
