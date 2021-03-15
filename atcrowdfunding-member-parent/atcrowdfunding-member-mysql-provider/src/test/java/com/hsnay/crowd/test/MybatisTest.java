package com.hsnay.crowd.test;

import com.hsany.crowd.CrowdMainClass;

import com.hsany.crowd.mapper.MemberPOMapper;

import com.hsany.crowd.mapper.ProjectPOMapper;
import com.hsnay.crowd.entity.po.MemberPO;
import com.hsnay.crowd.entity.vo.DetailProjectVO;
import com.hsnay.crowd.entity.vo.PortalProjectVO;
import com.hsnay.crowd.entity.vo.PortalTypeVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrowdMainClass.class)
public class MybatisTest {
    private Logger logger = LoggerFactory.getLogger(MybatisTest.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MemberPOMapper memberPOMapper;
    @Autowired
    private ProjectPOMapper projectPOMapper;
    @Test
    public void test() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void test2(){
        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectPortalTypeVOList();
        for (PortalTypeVO portalTypeVO : portalTypeVOS) {
            String name = portalTypeVO.getName();
            String remark = portalTypeVO.getRemark();
            logger.info("name" +name +"remark" +remark);
            List<PortalProjectVO> projectVOList = portalTypeVO.getProjectVOList();

            for (PortalProjectVO portalProjectVO : projectVOList) {
                if (portalProjectVO ==null)continue;
                String s = portalProjectVO.toString();
                logger.info(s);
            }
        }
    }
    @Test
    public void test3(){
        DetailProjectVO detailProjectVOS = projectPOMapper.selectDetailProjectVO(3);
        String s = detailProjectVOS.toString();
        logger.info(s);
    }
}
