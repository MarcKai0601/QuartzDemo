package com.example.quartzdemo.service;

import com.example.quartzdemo.job.EmailJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {

    @Autowired
    private Scheduler scheduler;

    public void scheduleJob(String jobName, String group, String cron, String toEmail) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
                .withIdentity(jobName, group)
                .usingJobData("to", toEmail)
                .build();

        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(cron);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger", group)
                .withSchedule(cronSchedule)
                .forJob(jobDetail)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void pauseJob(String jobName, String group) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobName, group));
    }

    public void resumeJob(String jobName, String group) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobName, group));
    }

    public void deleteJob(String jobName, String group) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(jobName, group));
    }

    public List<Map<String, Object>> listAllJobs() throws SchedulerException {
        List<Map<String, Object>> result = new ArrayList<>();
        for (String group : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> jobInfo = new HashMap<>();
                    jobInfo.put("jobName", jobKey.getName());
                    jobInfo.put("group", group);
                    jobInfo.put("nextFireTime", trigger.getNextFireTime());
                    jobInfo.put("state", scheduler.getTriggerState(trigger.getKey()).name());
                    result.add(jobInfo);
                }
            }
        }
        return result;
    }
}
