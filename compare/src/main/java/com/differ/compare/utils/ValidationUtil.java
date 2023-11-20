package com.differ.compare.utils;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 15:00
 */
import java.util.regex.Pattern;

public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        return Pattern.compile(regex).matcher(phoneNumber).matches();
    }
}
