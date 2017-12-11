package com.hrm.service;

import com.hrm.common.PageModel;
import com.hrm.entity.*;
import com.hrm.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hrmService")
public class HrmServiceImpl implements HrmService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private TrainMapper trainMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private DocumentMapper documentMapper;

    /** Dept 增删改查*/
    @Override
    public void removeDeptById(Integer id) throws Exception {
        deptMapper.deleteById(id);
    }

    @Override
    public void addDept(Dept dept) {
        deptMapper.save(dept);
    }

    @Override
    public void modifyDept(Dept dept) {
        deptMapper.update(dept);
    }

    @Override
    public List<Dept> findDept(Dept dept) {
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept);

        List<Dept> depts = deptMapper.selectByPage(params);
        return depts;
    }

    @Override
    public List<Dept> findDeptByPage(int pageIndex) {
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);

        int recordCount = deptMapper.getTotalCount();
        pageModel.setRecordCount(recordCount);

        Map<String, Object> params = new HashMap<>();
        if (recordCount > 0) {
            params.put("pageModel", pageModel);
        }

        return deptMapper.selectByPage(params);
    }

    @Override
    public Dept findDeptById(Integer id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<Dept> findAllDept() {
        return deptMapper.selectAllDept();
    }

    @Override
    public int getTotalDeptCount() {
        return deptMapper.getTotalCount();
    }


    /** Job 增删改查*/
    @Override
    public void removeJobById(Integer id) throws Exception {
        jobMapper.deleteById(id);
    }

    @Override
    public void addJob(Job job) {
        jobMapper.save(job);
    }

    @Override
    public void modifyJob(Job job) {
        jobMapper.update(job);
    }

    @Override
    public List<Job> findJobByPage(int pageIndex) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();
        pageModel.setPageIndex(pageIndex);

        int recordCount = jobMapper.getTotalCount();
        pageModel.setRecordCount(recordCount);

        if (recordCount > 0) {
            params.put("pageModel", pageModel);
        }

        return jobMapper.selectByPage(params);
    }

    @Override
    public Job findJobById(Integer id) {
        return jobMapper.selectById(id);
    }

    @Override
    public int getTotalJobCount() {
        return jobMapper.getTotalCount();
    }


    /** Train 增删改查*/
    @Override
    public void removeTrainById(Integer id) throws Exception {
        trainMapper.deleteById(id);
    }

    @Override
    public void addTrain(Train train) {
        trainMapper.save(train);
    }

    @Override
    public void modifyTrain(Train train) {
        trainMapper.update(train);
    }

    @Override
    public List<Train> findTrainByPage(int pageIndex) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();

        int recordCount = jobMapper.getTotalCount();
        pageModel.setRecordCount(recordCount);
        pageModel.setPageIndex(pageIndex);

        if (recordCount > 0) {
            params.put("pageModel", pageModel);
        }

        return trainMapper.selectByPage(params);
    }

    @Override
    public Train findTrainById(Integer id) {
        return trainMapper.selectById(id);
    }

    @Override
    public int getTotalTrainCount() {
        return trainMapper.getTotalCount();
    }

    @Override
    public List<Employee> findAllEmployee() {
        return employeeMapper.selectAllEmployee();
    }

    /** Notice 增删改查 */
    @Override
    public void removeNoticeById(Integer id) throws Exception {
        noticeMapper.deleteById(id);
    }

    @Override
    public void addNotice(Notice notice) {
        noticeMapper.save(notice);
    }

    @Override
    public void modifyNotice(Notice notice) {
        noticeMapper.update(notice);
    }

    @Override
    public List<Notice> findNoticeByPage(int pageIndex) {
        Map<String, Object> params = new HashMap<>();
        PageModel pageModel = new PageModel();

        int recordCount = jobMapper.getTotalCount();
        pageModel.setPageIndex(pageIndex);
        pageModel.setRecordCount(recordCount);

        if (recordCount > 0) {
            params.put("pageModel", pageModel);
        }

        return noticeMapper.selectByPage(params);
    }

    @Override
    public Notice findNoticeById(Integer id) {
        return noticeMapper.selectById(id);
    }

    @Override
    public int getTotalNoticeCount() {
        return noticeMapper.getTotalCount();
    }


    /** Document 增删改查*/
    @Override
    public void addDocument(Document document) {
        documentMapper.save(document);
    }

    @Override
    public void removeDocumentById(Integer id) {
        documentMapper.deleteById(id);
    }

    @Override
    public void modifyDocument(Document document) {
        documentMapper.update(document);
    }

    @Override
    public List<Document> findDocumentByPage(int pageIndex) {
        PageModel pageModel = new PageModel();
        Map<String, Object> params = new HashMap<>();

        int recordCount = documentMapper.getTotalCount();
        pageModel.setPageIndex(pageIndex);
        pageModel.setRecordCount(recordCount);

        if (recordCount > 0) {
            params.put("pageModel", pageModel);
        }

        return documentMapper.selectByPage(params);
    }

    @Override
    public Document findDocumentById(Integer id) {
        return documentMapper.selectById(id);
    }

    @Override
    public int getTotalDocumentCount() {
        return documentMapper.getTotalCount();
    }
}
