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


}
