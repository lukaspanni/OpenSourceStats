package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.lukaspanni.opensourcestats.R;

import java.util.Calendar;
import java.util.Date;

import de.lukaspanni.opensourcestats.android.ParcelableTimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;


public class DetailsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        view.findViewById(R.id.to_custom_time_span_details).setOnClickListener(v -> {
            //Show Date-Picker and open timeSpanDetails with picked time span
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
            builder.setCalendarConstraints(new CalendarConstraints.Builder().setEnd(Calendar.getInstance().getTimeInMillis()).build());
            builder.setTitleText(R.string.date_range_picker_title);
            MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
            picker.addOnPositiveButtonClickListener(selection -> {
                if (selection.first == null || selection.second == null)
                    return;

                TimeSpan timeSpan = new TimeSpan(new Date(selection.first), new Date(selection.second));
                clickNavigationHandler(view, timeSpan);
            });
            picker.show(getParentFragmentManager(), picker.toString());
        });

        view.findViewById(R.id.to_current_week_details).setOnClickListener(v -> {
            clickNavigationHandler(view, TimeSpanFactory.getCurrentWeek());
        });
        view.findViewById(R.id.to_last_week_details).setOnClickListener(v -> {
            clickNavigationHandler(view, TimeSpanFactory.getLastWeek());
        });

        view.findViewById(R.id.to_current_month_details).setOnClickListener(v -> {
            clickNavigationHandler(view, TimeSpanFactory.getCurrentMonth());
        });

        view.findViewById(R.id.to_last_month_details).setOnClickListener(v -> {
            clickNavigationHandler(view, TimeSpanFactory.getLastMonth());
        });

        return view;
    }

    private void clickNavigationHandler(View view, TimeSpan timeSpan) {
        Bundle b = new Bundle();
        b.putParcelable("timeSpan", new ParcelableTimeSpan(timeSpan));
        Navigation.findNavController(view).navigate(R.id.action_details_to_time_span_details, b);
    }
}