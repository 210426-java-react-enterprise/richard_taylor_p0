package comm.revature.util;


import com.revature.util.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryTest {

    @Test
    public void test_withValidCredentials() {
        //Arrange, act and Assert all in one

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Assert.assertNotNull(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
