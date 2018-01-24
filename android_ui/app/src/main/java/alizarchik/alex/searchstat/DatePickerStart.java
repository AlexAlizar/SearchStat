package alizarchik.alex.searchstat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by aoalizarchik.
 */

public class DatePickerStart extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String date1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // определяем текущую дату
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // создаем DatePickerDialog и возвращаем его
        Dialog picker = new DatePickerDialog(getActivity(), R.style.DialogTheme, this,
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

        Button tv = getActivity().findViewById(R.id.start_date);
        tv.setText(day + "." + (month + 1) + "." + year);
        date1 = tv.getText().toString();
        DailyStatActivity dailyStatActivity = (DailyStatActivity)getActivity();
        dailyStatActivity.setDate1(date1);
    }
}
