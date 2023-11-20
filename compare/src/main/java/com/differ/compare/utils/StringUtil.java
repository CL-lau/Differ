package com.differ.compare.utils;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 15:00
 */
public class StringUtil {

    public static String concatenateStrings(String... strings) {
        StringBuilder result = new StringBuilder();
        for (String str : strings) {
            if (str != null) {
                result.append(str);
            }
        }
        return result.toString();
    }

    public static String replaceString(String source, String target, String replacement) {
        return source.replace(target, replacement);
    }
}

