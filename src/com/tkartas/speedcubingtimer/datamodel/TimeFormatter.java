package com.tkartas.speedcubingtimer.datamodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeFormatter {

    public String convertToDateFormat(long timeInMillis){
        DateFormat df = new SimpleDateFormat("s.SS");
        double timeDev = (timeInMillis/1000);
        if (timeDev >= 10 && timeDev < 60) {
            df = new SimpleDateFormat("ss.SS");
        } else if (timeDev >= 60 && timeDev < 600) {
            df = new SimpleDateFormat("m:ss.SS");
        } else if (timeDev >= 600 && timeDev < 3600) {
            df = new SimpleDateFormat("mm:ss.SS");
        } else if(timeDev > 3600) {
            df = new SimpleDateFormat("HH:mm:ss.SS");
        }
        return df.format(timeInMillis);
    }

    public String cutLastMilli(String time) {
        String regEx;
        Pattern pattern;
        Matcher matcher;
        String millis=null;
        if (time.length() <= 6) {
            regEx = "(\\d+)\\.(\\d+)";
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(time);
            matcher.reset();
            while (matcher.find()) {
                millis = matcher.group(2);
            }
        } else if (time.length() <= 9) {
            regEx = "(\\d+):(\\d+)\\.(\\d+)";
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(time);
            matcher.reset();
            while (matcher.find()) {
                millis = matcher.group(3);
            }
        } else if (time.length() <= 12) {
            regEx = "(\\d+):(\\d+):(\\d+)\\.(\\d+)";
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(time);
            matcher.reset();
            while (matcher.find()) {
                millis = matcher.group(4);
            }
        }
        if(millis.length()>2){
            time=time.substring(0,time.length()-1);
        }
        return time;
    }
}
