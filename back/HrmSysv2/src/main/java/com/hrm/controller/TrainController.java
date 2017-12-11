package com.hrm.controller;

import com.hrm.common.Response;
import com.hrm.entity.Counter;
import com.hrm.entity.Employee;
import com.hrm.entity.Train;
import com.hrm.service.HrmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrainController {
    private final static Logger logger = LoggerFactory.getLogger(TrainController.class);

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    @RequestMapping(value = "/train/allteacher", method = RequestMethod.GET)
    public List<Employee> getAllEmployee () {
        logger.info("find all employee...");
        return hrmService.findAllEmployee();
    }

    @RequestMapping(value = "/train/page/{index}", method = RequestMethod.GET)
    public List<Train> getTrainByPage(@PathVariable("index") int index) {
        logger.info("index = {}", index);
        return hrmService.findTrainByPage(index);
    }

    @RequestMapping(value = "/train/count", method = RequestMethod.GET)
    public Response getTrainCount() {
        int count = hrmService.getTotalTrainCount();
        logger.info("train count = {}", count);
        Counter counter = new Counter("train", count);
        return new Response().success(counter);
    }

    @RequestMapping(value = "/train/{id}", method = RequestMethod.DELETE)
    public Response deleteTrainById(@PathVariable("id") int id) {
        logger.info("id = {}", id);

        try {
            hrmService.removeTrainById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return new Response().failure(e.getMessage());
        }

        return new Response().success();
    }

    @RequestMapping(value = "/train/{id}", method = RequestMethod.GET)
    public Train getTrainById(@PathVariable("id") int id) {
        logger.info("id = {}", id);
        Train train  = hrmService.findTrainById(id);
        logger.info("job = {}", train);
        return train;
    }

    @RequestMapping(value = "/train", method = RequestMethod.POST)
    public Response updateTrain(@RequestBody Train train) {
        logger.info("train = {}", train);
        hrmService.modifyTrain(train);
        return new Response().success();
    }

    @RequestMapping(value = "/train", method = RequestMethod.PUT)
    public Response addTrain(@RequestBody Train train) {
        logger.info("new train={}", train);
        hrmService.addTrain(train);
        return new Response().success();
    }
}


