package edu.tacoma.uw.momentum;

import java.util.regex.Pattern;

public class Account {
    private String email;
    private String password;

    // Email validation pattern
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    // Constructor
    public Account(String email, String password) {
        setEmail(email);  // Validate and set email
        setPassword(password); // Validate and set password
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email with validation
    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }


    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password with validation
    public void setPassword(String password) {
        if (isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid password format.");
        }
    }

    /**
     * Validates if the given input is a valid email address.
     *
     * @param email        The email to validate.
     * @return {@code true} if the input is a valid email. {@code false} otherwise.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // Constants for password validation
    private final static int PASSWORD_LEN = 6;

    /**
     * Validates if the given password is valid.
     * Valid password must be at least 6 characters long
     * with at least one digit and one symbol.
     *
     * @param password        The password to validate.
     * @return {@code true} if the input is a valid password. {@code false} otherwise.
     */
    public static boolean isValidPassword(String password) {
        boolean foundDigit = false, foundSymbol = false;
        if (password == null || password.length() < PASSWORD_LEN) return false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) foundDigit = true;
            if (!Character.isLetterOrDigit(password.charAt(i))) foundSymbol = true;
        }
        return foundDigit && foundSymbol;
    }
}
