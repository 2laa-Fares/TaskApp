package com.example.apit.task.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String convertDate(Date mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;
    }
}
