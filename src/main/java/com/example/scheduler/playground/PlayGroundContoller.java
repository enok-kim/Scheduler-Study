package com.example.scheduler.playground;

import com.example.scheduler.info.TimerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    }//runHelloWorldJob

    @GetMapping
    public List<TimerInfo> getAllRunningTimers(){
        return service.getAllRunningTimer();
    }
    @GetMapping("/{timerId}")
    public TimerInfo getRunningTimer(@PathVariable String timerId){
        return service.getRunningTimer(timerId);
    }

}
