package com.rxliuli.study.springtest.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author rxliuli
 */
public class StringUtilTest {
    private String strNull = null;
    private String strEmpty = "";
    private String strSome = "str";

    @Test
    public void isEmpty() {
        //测试 null
        assertThat(StringUtil.isEmpty(strNull))
                .isTrue();
        //测试 empty
        assertThat(StringUtil.isEmpty(strEmpty))
                .isTrue();
        //测试 some
        assertThat(StringUtil.isEmpty(strSome))
                .isFalse();
    }

    @Test
    public void isNotEmpty() {
        //测试 null
        assertThat(StringUtil.isNotEmpty(strNull))
                .isFalse();
        //测试 empty
        assertThat(StringUtil.isNotEmpty(strEmpty))
                .isFalse();
        //测试 some
        assertThat(StringUtil.isNotEmpty(strSome))
                .isTrue();
    }

    @Test
    public void isAnyEmpty() {
        assertThat(StringUtil.isAnyEmpty(strNull, strEmpty, strSome))
                .isTrue();
        assertThat(StringUtil.isAnyEmpty())
                .isFalse();
    }

    @Test
    public void isAllEmpty() {
        assertThat(StringUtil.isAllEmpty(strNull, strEmpty, strSome))
                .isFalse();
        assertThat(StringUtil.isAnyEmpty(strNull, strEmpty))
                .isTrue();
    }
}