package de.lukaspanni.opensourcestats.ui.custom_elements;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TimeSpanCallback timeSpanCallback;

    public DatePickerDialogFragment(TimeSpanCallback callback){
        this.timeSpanCallback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate((new Date()).getTime());
        return dialog;
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(timeSpanCallback == null)
            return;
        Date pickedDate = new Date(year-1900, month, dayOfMonth);
        TimeSpan timeSpan = TimeSpanFactory.getWeek(pickedDate);
        timeSpanCallback.callback(timeSpan);
    }
}
