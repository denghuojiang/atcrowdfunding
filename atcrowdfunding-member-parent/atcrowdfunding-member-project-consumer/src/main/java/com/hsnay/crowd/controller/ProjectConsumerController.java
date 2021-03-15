package com.hsnay.crowd.controller;

import com.hsnay.crowd.api.MySQLRemoteService;
import com.hsnay.crowd.config.OSSProperties;
import com.hsnay.crowd.entity.vo.*;
import com.hsnay.crowd.util.CrowdConstant;
import com.hsnay.crowd.util.CrowdUtil;
import com.hsnay.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectConsumerController {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @Autowired
    private OSSProperties ossProperties;

    private Logger logger = LoggerFactory.getLogger(ProjectConsumerController.class);

    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId,
                                   ModelMap modelMap) {
        ResultEntity<DetailProjectVO> projectDetailRemote = mySQLRemoteService.getProjectDetailRemote(projectId);
        String result = projectDetailRemote.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {
            DetailProjectVO detailProjectVO = projectDetailRemote.getData();
            String projectName = detailProjectVO.getProjectName();
            logger.info(projectName+"=================");
            System.out.println(projectName);
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_DETAIL_PROJECT, detailProjectVO);
        }
        return "project-show-detail";
    }

    @RequestMapping("/create/confirm.html")
    public String createConfirm(MemberConfirmInfoVO memberConfirmInfoVO,
                                HttpSession session,
                                ModelMap modelMap) {
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if (projectVO == null) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        // 将信息设置到projectvo
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute("member");
        Integer memberId = memberLoginVO.getId();
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberId);
        //判断是否成功
        String result = saveResultEntity.getResult();
        System.out.println(result);
        if (ResultEntity.FAILED.equals(result)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            return "project-confirm";
        }
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        return "redirect:http://localhost/project/create/success.html";
    }

    @RequestMapping("/create/project/information")
    public String createProject(
            ProjectVO projectVO,
            MultipartFile headerPicture,
            List<MultipartFile> detailPictureList,
            HttpSession session,
            ModelMap modelMap) throws IOException {

        // 一、完成头图的上传
        // 判断headerPicture对象是否为空
        boolean headerPictureEmpty = headerPicture.isEmpty();

        if (headerPictureEmpty) {
            // 头图为空，存入提示信息，且返回原本的页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "上传失败");
            return "project-launch";
        }
        // 头图不为空 进行上传操作
        ResultEntity<String> headerPictureResultEntity = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());
        // 判断是否上传成功
        String result = headerPictureResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {
            // 上传失败
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "上传失败");
            return "project-launch";
        } else {
            // 上传成功
            // 得到存入OSS服务器的文件名
            String headerPicturePath = headerPictureResultEntity.getData();

            // 存入ProjectVO对象
            projectVO.setHeaderPicturePath(headerPicturePath);
        }

        // 二、完成详情图片的上传

        // 创建用于存放详情图片的路径的List对象
        List<String> detailPicturePathList = new ArrayList<>();

        // 判断详情图片是否为空
        if (detailPictureList == null || detailPictureList.size() == 0) {
            // 详情图片为空，加入提示信息，返回原本页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "图片必选");
            return "project-launch";
        }
        // 详情图片不为空 遍历List
        for (MultipartFile detailPicture : detailPictureList) {
            // 判断当前MultipartFile是否有效
            if (detailPicture.isEmpty()) {
                // 当前图片为空，也返回原本的页面
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "图片必选");
                return "project-launch";
            }
            // 不为空，开始存放详情图片
            ResultEntity<String> detailPictureResultEntity = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());
            // 检查上传的结果
            String detailPictureResult = detailPictureResultEntity.getResult();
            if (ResultEntity.FAILED.equals(detailPictureResult)) {
                // 上传失败
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "上传失败");
                return "project-launch";
            }
            // 上传成功
            // 将当前上传后的路径放入list
            detailPicturePathList.add(detailPictureResultEntity.getData());
        }

        // 将detailPicturePathList存入ProjectVO对象
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 后续操作
        // 将ProjectVO对象放入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        // 进入下一个收集回报信息的页面
        return "redirect:http://localhost/project/return/info/page.html";
    }

    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturn(@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        ResultEntity<String> uploadFileToOSS = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());

        return uploadFileToOSS;
    }

    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            if (returnVOList == null || returnVOList.size() == 0) {
                returnVOList = new ArrayList<>();
                projectVO.setReturnVOList(returnVOList);
            }
            returnVOList.add(returnVO);
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

}