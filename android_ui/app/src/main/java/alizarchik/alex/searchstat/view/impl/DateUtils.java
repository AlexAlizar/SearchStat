package alizarchik.alex.searchstat.view.impl;

/**
 * Created by Olesia on 24.05.2018.
 */

class DateUtils {

    static String dateSet(int year, int month, int day){
        ++month;
        String date;
        if (day < 10 & month < 10) {
            date = (year + "-0" + (month) + "-0" + day);
        } else if (day < 10) {
            date = (year + "-" + (month) + "-0" + day);
        } else if (month < 10) {
            date = (year + "-0" + (month) + "-" + day);
        } else {
            date = (year + "-" + (month) + "-" + day);
        }
        return date;
    }
}
