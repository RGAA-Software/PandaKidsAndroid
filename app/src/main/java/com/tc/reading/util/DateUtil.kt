package com.tc.reading.util

import android.annotation.SuppressLint
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtil {

    data class SeparatedDate(val year: String, val month: String, val day: String);

    companion object {

        // format to: 2024-03-14
        @SuppressLint("SimpleDateFormat")
        fun fmtCurrentDay(): String {
            val fmt = SimpleDateFormat("yyyy-MM-dd");
            return fmt.format(Date());
        }

        fun getSeparatedDate(): SeparatedDate {
            // year
            val calendar = Calendar.getInstance();
            calendar.time = Date();
            val year = calendar.get(Calendar.YEAR);

            // month
            val monthNum = calendar.get(Calendar.MONTH) + 1;
            val months = DateFormatSymbols(Locale.ENGLISH).months;
            val month = months[monthNum];

            // day
            val day = calendar.get(Calendar.DAY_OF_MONTH);
            return SeparatedDate(year.toString(), month, day.toString());
        }

    }

}