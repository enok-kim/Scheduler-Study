package com.example.scheduler.timerservice;
import com.example.scheduler.info.TimerInfo;
import com.example.scheduler.utill.TimerUtill;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    private Scheduler scheduler;

    public SchedulerService(Scheduler scheduler){
        this.scheduler = scheduler;
    }//SchedulerService
    
    public void schedule(final Class jobClass, final TimerInfo info){
        final JobDetail jobDetail = TimerUtill.buildJobDetail(jobClass, info);
        final Trigger trigger = TimerUtill.buildTrigger(jobClass, info);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    @PostConstruct
    public void init(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }//catch
    }//init
    @PreDestroy
    public void PreDestory(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
