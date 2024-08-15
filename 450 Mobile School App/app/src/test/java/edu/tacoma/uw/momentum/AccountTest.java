package edu.tacoma.uw.momentum;

import static org.junit.Assert.*;
import org.junit.Test;

public class AccountTest {

    @Test
    public void testAccountConstructor() {
        // Use a valid email and password to test successful construction
        assertNotNull(new Account("valid.email@example.com", "password123!"));
    }

    @Test
    public void testAccountConstructorBadEmail() {
        try {
            // Explicitly use an invalid email to test error handling
            new Account("invalidemail", "password123!");
            fail("Account created with invalid email");
        } catch(IllegalArgumentException e) {
            // Expected this exception, test should pass
        }
    }

    @Test
    public void testSetEmailValid() {
        Account account = new Account("valid.email@example.com", "password123!");
        account.setEmail("new.valid.email@example.com");
        assertEquals("new.valid.email@example.com", account.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmailInvalid() {
        Account account = new Account("valid.email@example.com", "password123!");
        account.setEmail("bademail.com");  // This should throw IllegalArgumentException
    }

    @Test
    public void testSetPasswordValid() {
        Account account = new Account("valid.email@example.com", "password123!");
        account.setPassword("newpassword123!");
        assertEquals("newpassword123!", account.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPasswordInvalid() {
        Account account = new Account("valid.email@example.com", "password123!");
        account.setPassword("short");  // This should throw IllegalArgumentException
    }

    @Test
    public void testIsValidEmailTrue() {
        assertTrue(Account.isValidEmail("valid.email@example.com"));
    }

    @Test
    public void testIsValidEmailFalse() {
        assertFalse(Account.isValidEmail("invalidemail"));
    }

    @Test
    public void testIsValidPasswordTrue() {
        assertTrue(Account.isValidPassword("password123!"));
    }

    @Test
    public void testIsValidPasswordFalseShort() {
        assertFalse(Account.isValidPassword("p1!"));
    }

    @Test
    public void testIsValidPasswordFalseNoDigit() {
        assertFalse(Account.isValidPassword("password!"));
    }

    @Test
    public void testIsValidPasswordFalseNoSymbol() {
        assertFalse(Account.isValidPassword("password123"));
    }
}
