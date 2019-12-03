package com.capstone.notekeeper.CommonFiles;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getDateTime(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss");
        return simpleDateFormat.format(date);
    }
    public static String getFormatedDate(long  timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        return dateFormat.format(date);
    }
}
