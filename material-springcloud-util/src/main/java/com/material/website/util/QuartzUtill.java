/*package com.material.website.util;

import java.awt.Event;

import javax.annotation.Resource;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component //申明成为spring 组件 
public class QuartzUtill{
	
   @Autowired
    private Scheduler scheduler;
   
   @Override
    public void addQuartz(Event event) throws SchedulerException {
        JSONObject eventData = JSONObject.parseObject(event.getEventData());
        Date triggerDate = eventData.getDate("date");
        JobDetail job = JobBuilder.newJob(EventJob.class).withIdentity(event.getId().toString(), JOB_GROUP).usingJobData(buildJobDateMap(event)).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(event.getId().toString(), TRIGGER_GROUP).startAt(triggerDate).build();
		scheduler.scheduleJob(job, trigger);
    }
}
*/