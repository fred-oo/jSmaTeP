package com.kardass.jsmatep.parser;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
    
    private DateUtil() {
    }
    
    public static Date getDate(int year, int month, int dayOfMonth) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
