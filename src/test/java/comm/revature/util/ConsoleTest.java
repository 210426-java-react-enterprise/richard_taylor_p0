package comm.revature.util;

import com.revature.util.Console;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ConsoleTest {

    @Test
    public void test_getDate() {
        //Arrange
        LocalDateTime date = null;
        Console console = new Console();
        //Act
         date = console.getDate("Enter a Date");

        //Assert
        Assert.assertNotNull(date);
    }

}
