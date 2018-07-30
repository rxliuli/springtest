package com.rxliuli.study.springtest.util;

import java.util.Arrays;

/**
 * 用于测试的字符串工具类
 *
 * @author rxliuli
 */
public class StringUtil {
    /**
     * 判断是否为空
     *
     * @param string 要进行判断的字符串
     * @return 是否为 null 或者空字符串
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();

    }

    /**
     * 判断是否为空
     *
     * @param string 要进行判断的字符串
     * @return 是否为 null 或者空字符串
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 判断是否有字符串为空
     *
     * @param strings 要进行判断的一个或多个字符串
     * @return 是否有 null 或者空字符串
     */
    public static boolean isAnyEmpty(String... strings) {
        return Arrays.stream(strings)
                .anyMatch(StringUtil::isEmpty);
    }

    /**
     * 判断字符串是否全部为空
     *
     * @param strings 要进行判断的一个或多个字符串
     * @return 是否全部为 null 或者空字符串
     */
    public static boolean isAllEmpty(String... strings) {
        return Arrays.stream(strings)
                .allMatch(StringUtil::isEmpty);
    }
}
