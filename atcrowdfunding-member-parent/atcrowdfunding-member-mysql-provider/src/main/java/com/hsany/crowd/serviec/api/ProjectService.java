package com.hsany.crowd.serviec.api;

import com.hsnay.crowd.entity.vo.DetailProjectVO;
import com.hsnay.crowd.entity.vo.PortalTypeVO;
import com.hsnay.crowd.entity.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);
    List<PortalTypeVO> getPortalTypeVO();
    DetailProjectVO getDetailProjectVO(Integer projectId);
}
