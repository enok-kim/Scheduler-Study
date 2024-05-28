package com.example.scheduler.timerservice;
import com.example.scheduler.info.TimerInfo;
import com.example.scheduler.utill.TimerUtill;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.stream.Collectors;

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
    public List<TimerInfo> getAllRunningTimers(){
        try {
            // 스케줄러에 기록된 모든 작업의 JobKey를 가져옴
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey ->
                    {
                        try {
                            //JobKey에 해당하는  JobDetail을 가져옴
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }

        // 메서드는 Quartz 스케줄러에서 현재 실행 중인 모든 타이머 정보를 가져와 리스트로 반환.
        //스케줄러에 등록된 모든 작업의 키를 가져오고, 각 작업에 대한 상세 정보를 추출하여 TimerInfo 객체로 변환.
        //예외 발생 시 로그를 남기고 빈 리스트를 반환.

    }//Group Matcher

    public TimerInfo getRunningTimer(String timerId){
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));

            if (jobDetail == null){
                return null;
            }

            return (TimerInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (SchedulerException e) {
            LOG.error(e.getMessage(), e);
            return null;
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
