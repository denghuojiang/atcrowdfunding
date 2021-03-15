package com.hsany.crowd.mapper;


import com.hsnay.crowd.entity.po.ReturnPO;
import com.hsnay.crowd.entity.po.ReturnPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface ReturnPOMapper {
    long countByExample(ReturnPOExample example);

    int deleteByExample(ReturnPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReturnPO record);

    int insertSelective(ReturnPO record);

    List<ReturnPO> selectByExample(ReturnPOExample example);

    ReturnPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByExample(@Param("record") ReturnPO record, @Param("example") ReturnPOExample example);

    int updateByPrimaryKeySelective(ReturnPO record);

    int updateByPrimaryKey(ReturnPO record);

    void insertReturnPOBatch(@Param("returnPOList") ArrayList<ReturnPO> returnPOList,@Param("projectId") Integer projectId);
}