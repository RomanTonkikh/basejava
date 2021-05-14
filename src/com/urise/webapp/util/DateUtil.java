package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate getLocalDate(String date) {
        if (date.equals("Сейчас") || HtmlUtil.isEmpty(date))  {
            return NOW;
        }

        YearMonth yM = YearMonth.parse(date, FORMATTER);
        return LocalDate.of(yM.getYear(), yM.getMonth(), 1);
    }

    public static String getStringDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? "Сейчас" : date.format(FORMATTER);
    }
}
