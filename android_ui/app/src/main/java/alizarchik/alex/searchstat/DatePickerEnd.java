package alizarchik.alex.searchstat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aoalizarchik.
 */

public class DatePickerEnd extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String date2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // определяем текущую дату

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // создаем DatePickerDialog и возвращаем его
        Dialog picker = new DatePickerDialog(getActivity(), R.style.DialogTheme,this,
                year, month, day);
        return picker;
    }
    @Override
    public void onStart() {
        super.onStart();
        // добавляем кастомный текст для кнопки
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.ready));

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year,
                          int month, int day) {

        TextView tv = getActivity().findViewById(R.id.end_date);
        String monthName = new DateFormatSymbols().getMonths()[month];
        tv.setText(monthName + " " + day + ", " + year);
        ++month;
        if (day < 10 & month < 10) {
            date2 = (year + "-0" + (month) + "-0" + day);
        } else if (day < 10) {
            date2 = (year + "-" + (month) + "-0" + day);
        } else if (month < 10) {
            date2 = (year + "-0" + (month) + "-" + day);
        } else {
            date2 = (year + "-" + (month) + "-" + day);
        }
        DailyStatActivity dailyStatActivity = (DailyStatActivity)getActivity();
        dailyStatActivity.setDate2(date2);
    }
}
