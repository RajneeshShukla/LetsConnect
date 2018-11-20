package com.example.rajneeshshukla.letsconnect.utils;

import android.app.Activity;
import android.util.Patterns;

import com.example.rajneeshshukla.letsconnect.activities.register.RegisterActivity;

/**
 * This class has validation related method
 *
 * created by Rajneesh Shukla on 19/11/18
 */
public class Validate {
    private static final int  EMAIL_MAX_LENGTH = 60;
    private static final int NAME_MAX_LENGTH = 60;
    private static final int NAME_MIN_LENGTH = 1;
    public static final int PASSWORD_MAX_LENGTH = 15;
    public static final int PASSWORD_MIN_LENGTH = 5;
    private static final int PIN_CODE_MAX_LENGTH = 6;
    private static final int PIN_CODE_MIN_LENGTH = 4;
    public static final int YEAR_LENGTH = 4;
    public static final int MINIMUM_YEAR = 1900;
    private static final int PASSCODE_LENGTH = 4;
    /**
     * method validates if the input string is in email format or not
     *
     * @return boolean value depending upon if a string is a email id or not
     */
    public static boolean validateEmail(Activity activity, String emailId) {
        boolean returnValue = true;
        String message = null;

        // if email id is empty
        if (!validateField(emailId))
            returnValue = false;
        else if (emailId.length() > EMAIL_MAX_LENGTH) {
            message =  "Max length";//String.format(activity.getString(R.string.email_max_length_message), String.valueOf(EMAIL_MAX_LENGTH));
            returnValue = false;
        }
        // email id is not in correct format
        else if (!(Patterns.EMAIL_ADDRESS.matcher(emailId).matches())) {

            message = "Email formate is not correct";  // activity.getString(R.string.wrong_emailid_message);
            returnValue = false;
        }
        Utility.showLongText(activity, message);
        return returnValue;
    }


    /**
     * method validates if the input string is in password format or not
     *
     * @return boolean value depending upon if a string is a password format or not
     */
    public static boolean validatePassword(Activity activity, String password) {
        boolean returnValue = true;
        String message = null;

        // check if password is empty
        if (!validateField(password))
            returnValue = false;

            // if password is not of correct format
        else if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            message =  "Password is wrong"  ;//String.format(activity.getString(R.string.wrong_password_message), String.valueOf(PASSWORD_MIN_LENGTH), String.valueOf(PASSWORD_MAX_LENGTH));
            returnValue = false;
        }
        Utility.showLongText(activity, message);
        return returnValue;
    }

    /**
     * Method checks if password is same as confirm password or not
     */
    public static boolean validateConfirmPassword(Activity activity, String password, String confirmPassword) {
        boolean returnValue = false;

        //checks if password is valid and password is equal to confirm password
        if (validatePassword(activity, password) && !(confirmPassword.trim().length() == 0) && password.equals(confirmPassword))
            returnValue = true;
        else
            Utility.showLongText(activity, "Password is not same as confirm password");
        return returnValue;
    }

    /**
     * method checks validation of name field
     */
    public static boolean validateName(Activity activity, String name) {
        boolean returnValue = true;
        String message = null;
        if (!validateField( name))
            returnValue = false;
        else if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            message = "Wrong Name";  //String.format(activity.getString(R.string.wrong_name_message), String.valueOf(NAME_MIN_LENGTH),

            returnValue = false;
        }
        Utility.showLongText(activity, message);
        return returnValue;
    }

    /**
     * Method simply checks any string field if it is empty and displays proper message
     */
    public static boolean validateField( String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }


}
