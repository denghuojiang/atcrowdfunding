package com.hsnay.crowd.test;

import com.hsany.crowd.CrowdMainClass;

import com.hsany.crowd.mapper.MemberPOMapper;

import com.hsnay.crowd.entity.po.MemberPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrowdMainClass.class)
public class MybatisTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MemberPOMapper memberPOMapper;
    @Test
    public void test() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
    @Test
    public void test1(){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123123");
        MemberPO memberPO = new MemberPO(null, "jack", password, "杰克", "heshinuo@163.com", 1, 1, "jieke", "123123", 1);
        memberPOMapper.insert(memberPO);

    }

}
