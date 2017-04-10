package com.example.c4cib.map1111.Utility;

import android.app.AlertDialog;

import com.example.c4cib.map1111.ui.actLogin;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by c4cib on 21/11/2016.
 */

public class JavaUtility {

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public boolean verifyName(String lname)
    {
        lname = lname.trim();

        if(lname == null || lname.equals(""))
            return false;

        if(!lname.matches("[a-zA-Z]*"))
            return false;

        return true;
    }
    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
