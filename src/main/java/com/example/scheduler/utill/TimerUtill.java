package com.example.scheduler.utill;

import com.example.scheduler.info.TimerInfo;
import org.quartz.*;

import java.util.Date;

public final class TimerUtill {
    public TimerUtill(){}

    public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info){

        final JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put(jobClass.getSimpleName(), info);

        return JobBuilder
                .newJob(jobClass) //작업 클래스 지정 ß
                .withDescription(jobClass.getSimpleName()) // 작업에 대한 설명 설정
                .setJobData(jobDataMap)// 작업에 필요한 데이터 설정
                .build();// JobDetail 객체 생성
    }//Quartz의 스케줄러 라이브러리를 사용하여 스케줄링할 작업의 세부 정보를 설정하고 생성

    public static Trigger buildTrigger(final Class jobClass, final TimerInfo info){
        // SimpleScheduleBuilder 객체를 생성하고 반복 간격을 밀리초 단위로 설정
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(info.getRepeatIntervalMs());// 반복 간격 설정

        // 작업이 영구적으로 반복될지 또는 특정 횟수만큼 반복될지 결정
        if(info.isRunForever()){
            builder = builder.repeatForever(); // 영구 반복 설정
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1); // 특정 횟수 반복 설정
        }
        // Trigger 객체를 생성하고 설정
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName()) // 트리거 ID 설정
                .withSchedule(builder) // 스케줄 설정
                .startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs())) // 트리거 시작 시간 설정
                .build(); // Trigger 객체 생성
    }
}
