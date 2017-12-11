package com.hrm.service;

import com.hrm.entity.*;

import java.util.List;

public interface HrmService {

    /** Dept 增删改查*/
    void removeDeptById(Integer id) throws Exception;
    void addDept(Dept dept);
    void modifyDept(Dept dept);
    List<Dept> findDept(Dept dept);
    List<Dept> findDeptByPage(int pageIndex);
    Dept findDeptById(Integer id);
    List<Dept> findAllDept();
    int getTotalDeptCount();

    /** Job 增删改查*/
    void removeJobById(Integer id) throws Exception;
    void addJob(Job job);
    void modifyJob(Job job);
    List<Job> findJobByPage(int pageIndex);
    Job findJobById(Integer id);
    int getTotalJobCount();

    /** Train 增删改查*/
    void removeTrainById(Integer id) throws Exception;
    void addTrain(Train train);
    void modifyTrain(Train train);
    List<Train> findTrainByPage(int pageIndex);
    Train findTrainById(Integer id);
    int getTotalTrainCount();

    /** Employee 增删改查 */
    List<Employee> findAllEmployee();

    /** Notice 增删改查 */
    void removeNoticeById(Integer id) throws Exception;
    void addNotice(Notice notice);
    void modifyNotice(Notice notice);
    List<Notice> findNoticeByPage(int pageIndex);
    Notice findNoticeById(Integer id);
    int getTotalNoticeCount();


    /** Document 增删改查*/
    void addDocument(Document document);
    void removeDocumentById(Integer id);
    void modifyDocument(Document document);
    List<Document> findDocumentByPage(int pageIndex);
    Document findDocumentById(Integer id);
    int getTotalDocumentCount();


}
