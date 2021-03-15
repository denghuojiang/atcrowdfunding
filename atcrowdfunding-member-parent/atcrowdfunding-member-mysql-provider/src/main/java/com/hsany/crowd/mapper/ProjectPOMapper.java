package com.hsany.crowd.mapper;


import com.hsnay.crowd.entity.po.ProjectPO;
import com.hsnay.crowd.entity.po.ProjectPOExample;
import com.hsnay.crowd.entity.vo.DetailProjectVO;
import com.hsnay.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPOMapper {
    long countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertTypeRelationship(@Param("typeIdList") List<Integer> typeIdList, @Param("projectId") Integer projectId);

    void insertTagRelationship(@Param("tagList") List<Integer> tagIdList, @Param("projectId") Integer projectId);

    List<PortalTypeVO>  selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(@Param("projectId") Integer projectId);
   }