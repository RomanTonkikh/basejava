package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Position position) {
        return DateUtil.getStringDate(position.getStartDate()) + " - " +  DateUtil.getStringDate(position.getEndDate());
    }
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}

