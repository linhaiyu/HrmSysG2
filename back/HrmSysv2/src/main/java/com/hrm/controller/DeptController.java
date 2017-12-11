package com.hrm.controller;

import com.hrm.common.Response;
import com.hrm.entity.Counter;
import com.hrm.entity.Dept;
import com.hrm.service.HrmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    private final static Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    /**
     * 查询所有部门
     * @return 所有部门的List列表
     */
    @RequestMapping(value = "/dept", method = RequestMethod.GET)
    public List<Dept> getDept() {
        logger.info("getDept...");
        List<Dept> depts = hrmService.findAllDept();
        return depts;
    }

    /**
     * 根据页码来查询数据
     * @param index 页码
     * @return 查询结果
     */
    @RequestMapping(value = "/dept/page/{index}", method = RequestMethod.GET)
    public List<Dept> getDeptByPage (@PathVariable("index") int index) {
        logger.info("index = {}", index);
        return hrmService.findDeptByPage(index);
    }

    /**
     * 返回 dept 的总数
     * @return 返回Response对象，其中包含 dept 总数
     */
    @RequestMapping(value = "/dept/count", method = RequestMethod.GET)
    public Response getDeptCount () {
        int count = hrmService.getTotalDeptCount();
        Counter counter = new Counter("dept", count);
        return new Response().success(counter);
    }

    /**
     * 根据ID查询部门
     * @param id 部门id
     * @return 部门对象
     */
    @RequestMapping(value = "/dept/{id}", method = RequestMethod.GET)
    public Dept getDeptById(@PathVariable("id") int id) {
        logger.info("id = {}", id);

        Dept dept = hrmService.findDeptById(id);

        logger.info("get dept = {}", dept);
        return dept;
    }

    /**
     * 增加新部门
     * @param dept 待增加的部门
     * @return 增加结果
     */
    @RequestMapping(value = "/dept", method = RequestMethod.PUT)
    public Response addDept(@RequestBody Dept dept) {
        logger.info("new dept={}", dept);
        hrmService.addDept(dept);

        return new Response().success();
    }

    /**
     * 删除部门
     * @param id 待删除部门的id
     * @return 删除结果
     */
    @RequestMapping(value = "/dept/{id}", method = RequestMethod.DELETE)
    public Response deleteDept(@PathVariable("id") int id) {
        logger.info("id = {}", id);
        try {
            hrmService.removeDeptById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return new Response().failure(e.getMessage());
        }
        return new Response().success();
    }

    /**
     * 修改部门
     * @param dept 待修改的部门
     * @return 修改结果
     */
    @RequestMapping(value = "/dept", method = RequestMethod.POST)
    public Response updateDept(@RequestBody Dept dept) {
        logger.info("dept = {}", dept);
        hrmService.modifyDept(dept);

        return new Response().success();
    }
}
