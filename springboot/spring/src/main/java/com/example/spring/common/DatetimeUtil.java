package com.example.spring.common;

import cn.hutool.core.date.DateUtil;

public class DatetimeUtil {
    public static String[] getMonthDayList(String month) {
        int monthSize = DateUtil.lengthOfMonth(Integer.valueOf(month.substring(4)), DateUtil.isLeapYear(Integer.valueOf(month.substring(0, 4))));
        String[] monthDayList = new String[monthSize];
        for (int i = 0; i < monthSize; i++) {
            if (i < 9) {
                monthDayList[i] = month + "0" + (i + 1);
            } else {
                monthDayList[i] = month + (i + 1);
            }
        }
        return monthDayList;
    }
}
