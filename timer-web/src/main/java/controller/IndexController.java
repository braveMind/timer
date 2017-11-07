package controller;

import com.jun.timer.dto.JobDto;
import com.jun.timer.operate.JobOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by xunaiyang on 2017/7/18.
 */
@Controller
public class IndexController {
    @Autowired
    private JobOperationService jobOperationService;

    @RequestMapping(value = "/heartBeat")
    @ResponseBody
    public ResponseEntity<String> heartbeat() {
        return new ResponseEntity<String>("OK", org.springframework.http.HttpStatus.OK);
    }

    @RequestMapping(value = "/create")
    @ResponseBody
    public ResponseEntity<String> create() {
        JobDto jobDto = new JobDto();
        jobDto.setDescription("伯乐简历定时推送服务");
        jobDto.setJobName("测试");
        jobDto.setAppName("zhaopin-timer-service");
        jobDto.setClassName("boleResumeJob");
        jobDto.setMethodName("pushResumeToBole");
        jobDto.setParams("{\"mis\":\"wangmanlin\",\"tenantId\":\"1\",\"resumeId\":6,\"recommendJobId\":180582195534250020,\"ip\":\"180.153.132.29\"}");
        jobDto.setRouteStrategy(1);

        Integer jobId = jobOperationService.addJob("1", jobDto, new Date(System.currentTimeMillis() + 60000 * 120));

        return new ResponseEntity<String>(jobId.toString(), org.springframework.http.HttpStatus.OK);
    }

}
