package crowd;


import com.hsnay.crowd.mapper.AdminMapper;
import com.hsnay.crowd.entity.Admin;
import com.hsnay.crowd.service.api.AdminService;
import com.hsnay.crowd.service.impl.AdminServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class JdbcTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;
    @Test
    public void testInsert(){
        Admin admin = new Admin(null, "12345", "root", "灯火酱", "heshinuoi@gmail.com", null);
        int insert = adminMapper.insert(admin);
        System.out.println(insert);
    }
    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testLog(){
        Logger logger = LoggerFactory.getLogger(JdbcTest.class);
        logger.debug("hello i am debug");
        logger.debug("hello i am debug");
        logger.debug("hello i am debug");
        logger.info("hello info");
        logger.info("hello info");
        logger.info("hello info");
    }

    @Test
    public void testTx(){
        Admin admin = new Admin(null, "denghuo", "denghuo333", "灯火酱", "denghuo@qq.com", null);
        adminService.saveAdmin(admin);
    }
    //空指针异常的消息是null
    @Test
    public void test(){
        try {
            throw new NullPointerException();
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
        }

    }
    @Test
    public void addAdmin(){
        for (int i=1;i<234;i++){
            adminMapper.insert(new Admin(null,"login_acct"+i,"userpswd"+i,"userName"+i,"emil"+i,null));
        }
    }
}
