package com.hrm.controller;

import com.hrm.common.Response;
import com.hrm.entity.Counter;
import com.hrm.entity.Document;
import com.hrm.service.HrmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class DocumentController {
    private final static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    @Qualifier("hrmService")
    private HrmService hrmService;

    @RequestMapping(value = "/document/page/{index}", method = RequestMethod.GET)
    public List<Document> getDocumentByPage(@PathVariable("index") int index) {
        logger.info("index = {}", index);
        return hrmService.findDocumentByPage(index);
    }

    @RequestMapping(value = "/document/count", method = RequestMethod.GET)
    public Response getDocumentCount() {
        int count = hrmService.getTotalDocumentCount();
        logger.info("doc count = {}", count);
        Counter counter = new Counter("document", count);
        return new Response().success(counter);
    }

    @RequestMapping(value = "/document/{id}", method = RequestMethod.DELETE)
    public Response deleteDocumentById(@PathVariable("id") int id) {
        logger.info("id = {}", id);

        try {
            hrmService.removeDocumentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            return new Response().failure(e.getMessage());
        }

        return new Response().success();
    }

    @RequestMapping(value = "/document/{id}", method = RequestMethod.GET)
    public Document getDocumentById(@PathVariable("id") int id) {
        logger.info("id = {}", id);
        Document document = hrmService.findDocumentById(id);
        logger.info("document = {}", document);
        return document;
    }



    @RequestMapping(value = "/document", method = RequestMethod.POST)
    public Response updateDocument(MultipartFile file, Integer id, String title, String remark, HttpSession session) {
        logger.info("document = {}, {}, {}", id, title, remark);

        if (id == null) {
            return new Response().failure("id is null.");
        }

        Document document = hrmService.findDocumentById(id);

        if (file != null) {
            document.setFile(file);
            // 上传文件的存放路径
            String path = session.getServletContext().getRealPath("/upload/");
            logger.info("save file in: {}", path);
            // 将文件保存到文件系统
            String fileName = document.getFile().getOriginalFilename();

            try {
                document.getFile().transferTo(new File(path + File.separator + fileName));
            } catch (IOException e) {
                e.printStackTrace();
                return new Response().failure(e.getMessage());
            }


            document.setFileName(file.getOriginalFilename());
        }

        document.setTitle(title);
        document.setRemark(remark);

        hrmService.modifyDocument(document);
        return new Response().success();
    }

    @RequestMapping(value = "/document/add", method = RequestMethod.POST)
    public Response addDocument(MultipartFile file, String title, String remark, HttpSession session) {
        logger.info("new document={}, {}, {}", file, title, remark);

        Document document = new Document();
        document.setFile(file);
        document.setTitle(title);
        document.setRemark(remark);

        // 保存文件
        String path = session.getServletContext().getRealPath("/upload/");
        logger.info("save file in: {}", path);

        // 将文件保存到文件系统
        String fileName = document.getFile().getOriginalFilename();
        try {
            document.getFile().transferTo(new File(path + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return new Response().failure(e.getMessage());
        }

        document.setFileName(fileName);

        hrmService.addDocument(document);
        return new Response().success();
    }


//
//    @RequestMapping(value = "/document", method = RequestMethod.POST)
//    public Response updateDocument(Document document, HttpSession session) {
//        logger.info("document = {}", document);
//
//        // 上传文件的存放路径
//        String path = session.getServletContext().getRealPath("/upload/");
//        logger.info("save file in: {}", path);
//
//        // 将文件保存到文件系统
//        String fileName = document.getFile().getOriginalFilename();
//        try {
//            document.getFile().transferTo(new File(path + File.separator + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Response().failure(e.getMessage());
//        }
//
//        document.setFileName(fileName);
//
//        hrmService.modifyDocument(document);
//        return new Response().success();
//    }
//
//    @RequestMapping(value = "/document", method = RequestMethod.PUT)
//    public Response addDocument(@RequestBody Document document, HttpSession session) {
//        logger.info("new document={}", document);
//
//        // 保存文件
//        String path = session.getServletContext().getRealPath("/upload/");
//        logger.info("save file in: {}", path);
//
//        // 将文件保存到文件系统
//        String fileName = document.getFile().getOriginalFilename();
//        try {
//            document.getFile().transferTo(new File(path + File.separator + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Response().failure(e.getMessage());
//        }
//
//        document.setFileName(fileName);
//
//        hrmService.addDocument(document);
//        return new Response().success();
//    }
}
