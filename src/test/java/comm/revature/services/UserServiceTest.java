package comm.revature.services;

import com.revature.Exceptions.InvalidRequestException;
import com.revature.Exceptions.ResourcePersistenceException;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.List;
import com.revature.util.PoorArrayList;
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
    public void test_registerUserWithValidUserAndTakenUsername() {
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
    public void test_registerUserWithValidUserAndTakenEmail() {
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

    @Test
    public void test_getUserNameFromAccountWithEmptyString() {
        int id = 0;
        when(mockAccountDao.getUserNameFromAccount((anyInt()))).thenReturn("");

        try {
            sut.getUserNameFromAccount(id);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
        } finally {
            verify(mockAccountDao, times(1)).getUserNameFromAccount(anyInt());
        }

    }

    @Test
    public void test_getUserNameFromAccountWithValidString() {
        int id = 0;
        when(mockAccountDao.getUserNameFromAccount(anyInt())).thenReturn("some_string");

        String name = "";
        try {
            name = sut.getUserNameFromAccount(id);
        } catch (Exception e) {
            //swallow, should not throw exception
        } finally {
            assertEquals("some_string", name);
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void test_getAccountByNameNoFoundAccount() throws InvalidRequestException {
        //Arrange
        List<Account> accounts = new PoorArrayList<>();
        accounts.add(new Account(0, 1.0, "name"));

        //Act
        Account account = sut.getAccountByName(accounts, "nonane");
    }

    @Test
    public void test_getAccountByNameWithFoundAccount() throws InvalidRequestException {
        //Arrange
        List<Account> accounts = new PoorArrayList<>();
        accounts.add(new Account(0, 1.0, "name"));
        accounts.add(new Account(1, 1.0, "some_other_name"));
        Account expected = new Account(0, 1.0, "name");

        //Act
        Account actual = sut.getAccountByName(accounts, "name");

        //Assert
        assertEquals(expected.getName(), actual.getName());
    }
}

