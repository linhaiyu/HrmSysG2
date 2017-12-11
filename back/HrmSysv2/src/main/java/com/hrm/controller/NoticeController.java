package com.hrm.controller;

import com.hrm.common.Response;
import com.hrm.entity.Counter;
import com.hrm.entity.Notice;
import com.hrm.service.HrmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoticeController {
    private final static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;


    @RequestMapping(value = "/notice/page/{index}", method = RequestMethod.GET)
    public List<Notice> getNoticeByPage(@PathVariable("index") int index) {
        logger.info("index = {}", index);
        return hrmService.findNoticeByPage(index);
    }

    @RequestMapping(value = "/notice/count", method = RequestMethod.GET)
    public Response getNoticeCount() {
        int count = hrmService.getTotalNoticeCount();
        logger.info("notice count = {}", count);
        Counter counter = new Counter("notice", count);
        return new Response().success(counter);
    }

    @RequestMapping(value = "/notice/{id}", method = RequestMethod.DELETE)
    public Response deleteNoticeById(@PathVariable("id") int id) {
        logger.info("id = {}", id);

        try {
            hrmService.removeNoticeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return new Response().failure(e.getMessage());
        }

        return new Response().success();
    }

    @RequestMapping(value = "/notice/{id}", method = RequestMethod.GET)
    public Notice getNoticeById(@PathVariable("id") int id) {
        logger.info("id = {}", id);
        Notice notice = hrmService.findNoticeById(id);
        logger.info("notice = {}", notice);
        return notice;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.POST)
    public Response updateNotice(@RequestBody Notice notice) {
        logger.info("notice = {}", notice);
        hrmService.modifyNotice(notice);
        return new Response().success();
    }

    @RequestMapping(value = "/notice", method = RequestMethod.PUT)
    public Response addNotice(@RequestBody Notice notice) {
        logger.info("new notice={}", notice);
        hrmService.addNotice(notice);
        return new Response().success();
    }
}
