package comm.revature.services;

import com.revature.Exceptions.InvalidRequestException;
import com.revature.Exceptions.ResourcePersistenceException;
import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.List;
import com.revature.util.PoorArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Test
    public void test_recordTransactionWithValidData() {
        //Arrange
        Transaction before = null;
        Transaction transaction = new Transaction(1, "name", 1, "name", 1, 1.0, LocalDateTime.now(), "deposit");
        String sender = "sender";
        int senderID = 1;
        int recipientID = 1;
        double amount = 1.0;
        when(mockAccountDao.saveTransaction(anyString(), anyInt(), anyString(), anyInt(), anyString(), anyDouble())).thenReturn(Optional.of(transaction));
        when(mockAccountDao.getUserNameFromAccount(anyInt())).thenReturn("recipient");

        //Act
        try {
            before = sut.recordTransaction(sender, senderID, recipientID, "deposit", amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(before);
    }

    @Test
    public void test_recordTransactionWithInvalidData() {

        when(mockAccountDao.saveTransaction(anyString(), anyInt(), anyString(), anyInt(), anyString(), anyDouble())).thenReturn(null);
        when(mockAccountDao.getUserNameFromAccount(anyInt())).thenReturn("recipient");

        try {
            sut.recordTransaction(null, 0, 0, null, 0);
        } catch (Exception e) {
            assertTrue(e instanceof ResourcePersistenceException);
        }
    }

    @Test
    public void test_hasAccountNameWithFoundAccount() {
        //Arrange
        List<Account> accounts = new PoorArrayList<>();
        accounts.add(new Account(1, 1.0, "name"));
        accounts.add(new Account(2, 1.0, "second_name"));
        accounts.add(new Account(3, 1.0, "third_name"));

        when(mockAccountDao.getAccountsByUserID(any())).thenReturn(accounts);

        boolean found = false;

        //Act
        found = sut.hasAccountName(new User(), "name");

        //Assert
        assertTrue(found);
    }

    @Test
    public void test_hasAccountNameWithNotFoundAccount() {
        //Arrange
        List<Account> accounts = new PoorArrayList<>();
        accounts.add(new Account(1, 1.0, "name"));
        accounts.add(new Account(2, 1.0, "second_name"));
        accounts.add(new Account(3, 1.0, "third_name"));

        when(mockAccountDao.getAccountsByUserID(any())).thenReturn(accounts);

        boolean found;

        //Act
        found = sut.hasAccountName(new User(), "fourth_name");

        //Assert
        assertFalse(found);
    }

    @Test
    public void test_openUserAccountWithValidData() {
        User validUser = new User(0, "un", "pw", "something@mail.com",
                "fn", "ln", LocalDateTime.now(), 23);
        String name = "name2";
        double initialBalance = 1.0;
        List<Account> accounts = new PoorArrayList<>();
        accounts.add(new Account(1, 1.0, "name")); //the input string and account name are different
        when(mockAccountDao.getAccountsByUserID(validUser)).thenReturn(accounts);

        try {
            sut.openUserAccount(validUser, name, initialBalance);
        } catch(Exception e) {
            e.printStackTrace();
        }
        verify(mockAccountDao, times(1)).openAccount(anyInt(), anyString(), anyDouble());
    }

    @Test
    public void test_openUserAccountWithInvalidData() {
        User validUser = new User(0, "un", "pw", "something@mail.com",
                "fn", "ln", LocalDateTime.now(), 23);
        String name = "name";
        double initialBalance = 1.0;
        List<Account> accounts = new PoorArrayList<>();
        accounts.add(new Account(1, 1.0, "name")); //the input string and account name are the same
        when(mockAccountDao.getAccountsByUserID(validUser)).thenReturn(accounts);

        try {
            sut.openUserAccount(validUser, name, initialBalance);
        } catch(Exception e) {
            assertTrue(e instanceof InvalidRequestException);
        }
        verify(mockAccountDao, times(0)).openAccount(anyInt(), anyString(), anyDouble());
    }

}

