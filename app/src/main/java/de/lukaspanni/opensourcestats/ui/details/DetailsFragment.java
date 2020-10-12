package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.util.DateUtility;


public class DetailsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        view.findViewById(R.id.to_current_week_details).setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putParcelable("timeSpan", DateUtility.getCurrentWeek());
            Navigation.findNavController(view).navigate(R.id.action_details_to_week_details, b);
        });
        view.findViewById(R.id.to_last_week_details).setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putParcelable("timeSpan", DateUtility.getLastWeek());
            Navigation.findNavController(view).navigate(R.id.action_details_to_week_details, b);
        });

        view.findViewById(R.id.to_current_month_details).setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putParcelable("timeSpan", DateUtility.getCurrentMonth());
            Navigation.findNavController(view).navigate(R.id.action_details_to_week_details, b);
        });

        view.findViewById(R.id.to_last_month_details).setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putParcelable("timeSpan", DateUtility.getLastMonth());
            Navigation.findNavController(view).navigate(R.id.action_details_to_week_details, b);
        });



        return view;
    }
}