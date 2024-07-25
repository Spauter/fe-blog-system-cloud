package com.bs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsValudEmailAddressUtil {
    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w\\.-]+@[\\w\\.-]+\\.([a-z]{2,4}|[\\d]{1,3})(\\.[a-z]{2,4})?$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
}
