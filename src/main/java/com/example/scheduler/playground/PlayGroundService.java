package com.example.scheduler.playground;

import com.example.scheduler.info.TimerInfo;
import com.example.scheduler.jobs.HelloWorldJob;
import com.example.scheduler.timerservice.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Timer;

@Service
public class PlayGroundService {
    private final SchedulerService scheduler;

    @Autowired
    public PlayGroundService(final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }
    public void runHelloWorldJob(){
        TimerInfo info = new TimerInfo();
        info.setTotalFireCount(5);
        info.setRepeatIntervalMs(2000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callBack data");
        scheduler.schedule(HelloWorldJob.class, info);
    }//runHelloWorldJob

    public List<TimerInfo> getAllRunningTimer(){
        return scheduler.getAllRunningTimers();
    }
    public TimerInfo getRunningTimer(String timerId){
        return scheduler.getRunningTimer(timerId);
    }
}
