package com.hsany.crowd.serviec.impl;

import com.hsany.crowd.mapper.*;
import com.hsany.crowd.serviec.api.ProjectService;
import com.hsnay.crowd.entity.po.MemberConfirmInfoPO;
import com.hsnay.crowd.entity.po.MemberLaunchInfoPO;
import com.hsnay.crowd.entity.po.ProjectPO;
import com.hsnay.crowd.entity.po.ReturnPO;
import com.hsnay.crowd.entity.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectPOMapper projectPOMapper;
    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Autowired
    private ReturnPOMapper returnPOMapper;
    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        ProjectPO projectPO = new ProjectPO();
        BeanUtils.copyProperties(projectVO, projectPO);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(date);
        projectPO.setMemberid(memberId);
        //表示状态即将开始
        projectPO.setStatus(0);
        projectPOMapper.insertSelective(projectPO);
        Integer projectId = projectPO.getId();
        //保存分类 的关联关系信息
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList, projectId);
        // 保存 tag 关联信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList, projectId);
        //保存图片路径
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(detailPicturePathList, projectId);
        //保存发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

        //保存回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        ArrayList<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList, projectId);

        //保存确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }

    @Override
    public List<PortalTypeVO> getPortalTypeVO() {
        return projectPOMapper.selectPortalTypeVOList();
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
        Integer status = detailProjectVO.getStatus();
        switch (status) {
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 3:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 4:
                detailProjectVO.setStatusText("已关闭");
                break;
        }
        String deployDate = detailProjectVO.getDeployDate();
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = simpleDateFormat.parse(deployDate);
            long currentDateTimeStamp = currentDate.getTime();
            // 获取众筹日期时间戳
            long deployDayTimeStamp = deployDay.getTime();
            // 计算已经过去的天数
            long pastDays = (currentDateTimeStamp - deployDayTimeStamp) / 1000 / 60 / 60 / 24;
            // 获得众筹持续时间
            Integer totalDays = detailProjectVO.getDay();
            Integer lastDays = (int) (totalDays - pastDays);
            detailProjectVO.setLastDay(lastDays);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return detailProjectVO;
    }
}
