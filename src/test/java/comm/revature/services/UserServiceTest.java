package comm.revature.services;

import com.revature.Exceptions.InvalidRequestException;
import com.revature.Exceptions.ResourcePersistenceException;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.User;
import com.revature.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService sut;
    private UserDAO mockUserDao;
    private AccountDAO mockAccountDao;

    @Before
    public void setup() {
        mockUserDao = mock(UserDAO.class);
        mockAccountDao = mock(AccountDAO.class);
        sut = new UserService(mockUserDao, mockAccountDao);
    }

    @After
    public void tearDown() {
        mockUserDao = null;
        mockAccountDao = null;
        sut = null;
    }

    @Test
    public void test_withValidUserAndAvailablePassword() throws ResourcePersistenceException, InvalidRequestException {
        //arrange
        User validUser = new User(0, "un", "pw", "something@mail.com",
                "fn", "ln", LocalDateTime.now(), 23);
        User expectedResult = new User(1, "un", "pw", "something@mail.com",
                "fn", "ln", LocalDateTime.now(), 23);
        when(mockUserDao.isUserNameAvailable(anyString())).thenReturn(true);
        when(mockUserDao.isEmailAvailable(anyString())).thenReturn(true);
        when(mockUserDao.save(validUser)).thenReturn(expectedResult);

        //Act
        User actualResult = sut.registerUser(validUser);

        //Assert
        assertEquals(expectedResult, actualResult);
        verify(mockUserDao, times(1)).isEmailAvailable(anyString());
        verify(mockUserDao, times(1)).isUserNameAvailable(anyString());
        verify(mockUserDao, times(1)).save(any());
    }

    @Test
    public void test_withValidUserAndTakenUsername() {
        //Arrange
        when(mockUserDao.isUserNameAvailable(anyString())).thenReturn(false);
        when(mockUserDao.isEmailAvailable(anyString())).thenReturn(true);

        //Act
        try {
            sut.registerUser(new User(0, "un", "pw", "something@mail.com",
                    "fn", "ln", LocalDateTime.now(), 23));
        } catch (Exception e) {
            assertTrue(e instanceof ResourcePersistenceException);
        } finally {
            verify(mockUserDao, times(0)).save(any());
        }
    }

    @Test
    public void test_withValidUserAndTakenEmail() {
        //Arrange
        when(mockUserDao.isUserNameAvailable(anyString())).thenReturn(true);
        when(mockUserDao.isEmailAvailable(anyString())).thenReturn(false);

        //Act
        try {
            sut.registerUser(new User(0, "un", "pw", "something@mail.com",
                    "fn", "ln", LocalDateTime.now(), 23));
        } catch (Exception e) {
            assertTrue(e instanceof ResourcePersistenceException);
        } finally {
            verify(mockUserDao, times(0)).save(any());
        }
    }

}
