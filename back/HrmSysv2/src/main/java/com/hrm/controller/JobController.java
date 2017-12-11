package com.hrm.controller;

import com.hrm.common.Response;
import com.hrm.entity.Counter;
import com.hrm.entity.Job;
import com.hrm.service.HrmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {
    private final static Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;


    @RequestMapping(value = "/job/page/{index}", method = RequestMethod.GET)
    public List<Job> getJobByPage(@PathVariable("index") int index) {
        logger.info("index = {}", index);
        return hrmService.findJobByPage(index);
    }

    @RequestMapping(value = "/job/count", method = RequestMethod.GET)
    public Response getJobCount() {
        int count = hrmService.getTotalJobCount();
        logger.info("job count = {}", count);
        Counter counter = new Counter("job", count);
        return new Response().success(counter);
    }

    @RequestMapping(value = "/job/{id}", method = RequestMethod.DELETE)
    public Response deleteJobById(@PathVariable("id") int id) {
        logger.info("id = {}", id);

        try {
            hrmService.removeJobById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return new Response().failure(e.getMessage());
        }

        return new Response().success();
    }

    @RequestMapping(value = "/job/{id}", method = RequestMethod.GET)
    public Job getJobById(@PathVariable("id") int id) {
        logger.info("id = {}", id);
        Job job = hrmService.findJobById(id);
        logger.info("job = {}", job);
        return job;
    }

    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public Response updateJob(@RequestBody Job job) {
        logger.info("job = {}", job);
        hrmService.modifyJob(job);
        return new Response().success();
    }

    @RequestMapping(value = "/job", method = RequestMethod.PUT)
    public Response addJob(@RequestBody Job job) {
        logger.info("new job={}", job);
        hrmService.addJob(job);
        return new Response().success();
    }
}
