package com.example.instgran;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeAgo {
    public final static String monthAgo = "m ";
    public final static String weekAgo = "w ";
    public final static String daysAgo = "d ";
    public final static String hoursAgo = "h ";
    public final static String minAgo = "m ";
    public final static String secAgo = " Just now ";

    public final static String monthAgoAr = " شهر ";
    public final static String weekAgoAr = " أسبوع ";
    public final static String daysAgoAr = " يوم ";
    public final static String hoursAgoAr = " سنة ";
    public final static String minAgoAr = " دقيقة ";
    public final static String secAgoAr = " الأن ";
    static int second = 1000; // milliseconds
    static int minute = 60;
    static int hour = minute * 60;
    static int day = hour * 24;
    static int week = day * 7;
    static int month = day * 30;
    static int year = month * 12;

    @SuppressLint("SimpleDateFormat")
    public static String DateDifference(long fromDate) {
        long diff = 0;
        long ms2 = System.currentTimeMillis();
        // get difference in milli seconds
        diff = ms2 - fromDate;

        int diffInSec = Math.abs((int) (diff / (second)));
        String difference = "";
        if (diffInSec < minute) {
            if (Locale.getDefault().getLanguage() == "en") {
                difference = secAgo;
            } else {
                difference = secAgoAr;
            }

        } else if ((diffInSec / hour) < 1) {

            if (Locale.getDefault().getLanguage() == "en") {
                difference = (diffInSec / minute) + minAgo;
            } else {
                difference = (diffInSec / minute) + minAgoAr;
            }

        } else if ((diffInSec / day) < 1) {

            if (Locale.getDefault().getLanguage() == "en") {
                difference = (diffInSec / hour) + hoursAgo;
            } else {
                difference = (diffInSec / hour) + hoursAgoAr;
            }

        } else if ((diffInSec / week) < 1) {

            if (Locale.getDefault().getLanguage() == "en") {
                difference = (diffInSec / day) + daysAgo;
            } else {
                difference = (diffInSec / day) + daysAgoAr;
            }

        } else if ((diffInSec / month) < 1) {

            if (Locale.getDefault().getLanguage() == "en") {
                difference = (diffInSec / week) + weekAgo;
            } else {
                difference = (diffInSec / week) + weekAgoAr;
            }

        } else if ((diffInSec / year) < 1) {

            if (Locale.getDefault().getLanguage() == "en") {
                difference = (diffInSec / month) + monthAgo;
            } else {
                difference = (diffInSec / month) + monthAgoAr;
            }

        } else {
            // return date
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(fromDate);

            SimpleDateFormat format_before = new SimpleDateFormat(
                    "yyyy MM-dd hh:mm:ss");

            difference = format_before.format(c.getTime());
        }


//        Log.e("time difference is: ","" + difference);
        return difference;
    }
}
