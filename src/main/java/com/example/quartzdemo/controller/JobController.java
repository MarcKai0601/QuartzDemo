package com.example.quartzdemo.controller;

import com.example.quartzdemo.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/add")
    public String addJob(@RequestParam(name = "jobName") String jobName,
                         @RequestParam(name = "group", defaultValue = "default") String group,
                         @RequestParam(name = "cron") String cron,
                         @RequestParam(name = "toEmail") String toEmail) throws SchedulerException {
        jobService.scheduleJob(jobName, group, cron, toEmail);
        return "Job scheduled: " + jobName;
    }

    @PutMapping("/pause")
    public String pauseJob(@RequestParam(name = "jobName") String jobName,
                           @RequestParam(name = "group", defaultValue = "default") String group) throws SchedulerException {
        jobService.pauseJob(jobName, group);
        return "Job paused: " + jobName;
    }

    @PutMapping("/resume")
    public String resumeJob(@RequestParam(name = "jobName") String jobName,
                            @RequestParam(name = "group", defaultValue = "default") String group) throws SchedulerException {
        jobService.resumeJob(jobName, group);
        return "Job resumed: " + jobName;
    }

    @DeleteMapping
    public String deleteJob(@RequestParam(name = "jobName") String jobName,
                            @RequestParam(name = "group", defaultValue = "default") String group) throws SchedulerException {
        jobService.deleteJob(jobName, group);
        return "Job deleted: " + jobName;
    }

    @GetMapping
    public List<Map<String, Object>> listJobs() throws SchedulerException {
        return jobService.listAllJobs();
    }
}
