package com.example.scheduler.playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timer")
public class PlayGroundContoller {

    private final PlayGroundService service;

    @Autowired PlayGroundContoller(PlayGroundService service){
        this.service = service;
    }
    @PostMapping("/runhelloworld")
    public void runHelloWorldJob(){
        service.runHelloWorldJob();
    }
}
