package com.jun.timer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xunaiyang on 2017/9/21.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/heartBeat")
    @ResponseBody
    public ResponseEntity<String> heartbeat() {
        return new ResponseEntity<String>("OK", org.springframework.http.HttpStatus.OK);
    }
}
