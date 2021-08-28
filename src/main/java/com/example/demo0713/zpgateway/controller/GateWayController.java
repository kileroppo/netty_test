package com.example.demo0713.zpgateway.controller;

import com.example.demo0713.zpgateway.inbound.InboundServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GateWayController {

    @Autowired
    InboundServer inboundServer;

    @RequestMapping("/hello")
    public String getFirstReq(){
        inboundServer.handlerReq();

        return "handlering....";
    }
}
