package com.hsnay.crowd.api;


import com.hsnay.crowd.entity.po.MemberPO;
import com.hsnay.crowd.entity.vo.DetailProjectVO;
import com.hsnay.crowd.entity.vo.PortalTypeVO;
import com.hsnay.crowd.entity.vo.ProjectVO;
import com.hsnay.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("atguigu-crowd-mysql")
public interface MySQLRemoteService {
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/save/member/remote")
    ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);

    @RequestMapping("get/portal/type/project/data")
    ResultEntity<List<PortalTypeVO>> getPortalTypeProjectData();

    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getProjectDetailRemote(@PathVariable("projectId") Integer projectId);

}
