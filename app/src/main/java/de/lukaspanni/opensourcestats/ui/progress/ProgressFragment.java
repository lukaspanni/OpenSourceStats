package de.lukaspanni.opensourcestats.ui.progress;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.lukaspanni.opensourcestats.R;

import java.text.DecimalFormat;

import de.lukaspanni.opensourcestats.MainActivity;
import de.lukaspanni.opensourcestats.ui.card.ProgressCard;

public class ProgressFragment extends Fragment {

    private ProgressViewModel progressViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);


        ProgressCard progressOverviewCard = root.findViewById(R.id.progress_card);

        progressViewModel.getCurrentWeekContributions().observe(getViewLifecycleOwner(), progressOverviewCard::setCurrentPeriodContributions);
        progressViewModel.getLastWeekContributions().observe(getViewLifecycleOwner(), progressOverviewCard::setLastPeriodContributions);

        Activity parentActivity = getActivity();
        assert parentActivity != null;
        if(parentActivity.getClass() == MainActivity.class){
            progressViewModel.loadData(((MainActivity) parentActivity).getAuthHandler());
        }else{
            throw new UnsupportedOperationException("Cannot use RepositoryList from other Activity");
        }

        return root;
    }


}