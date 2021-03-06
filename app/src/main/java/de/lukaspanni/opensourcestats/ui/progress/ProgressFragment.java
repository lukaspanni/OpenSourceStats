package de.lukaspanni.opensourcestats.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.ui.DataAccessFragment;
import de.lukaspanni.opensourcestats.ui.custom_elements.card.ProgressCard;

public class ProgressFragment extends DataAccessFragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProgressViewModel progressViewModel =
                ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);

        SwipeRefreshLayout refresh = root.findViewById(R.id.progress_refresh);
        refresh.setOnRefreshListener(() -> {
            loadData(true);
            refresh.setRefreshing(false);
        });

        ProgressCard weeklyProgressCard = root.findViewById(R.id.progress_card_weekly);
        ProgressCard monthlyProgressCard = root.findViewById(R.id.progress_card_monthly);

        progressViewModel.getCurrentWeekContributions().observe(getViewLifecycleOwner(), weeklyProgressCard::setCurrentPeriodContributions);
        progressViewModel.getLastWeekContributions().observe(getViewLifecycleOwner(), weeklyProgressCard::setLastPeriodContributions);

        progressViewModel.getCurrentMonthContributions().observe(getViewLifecycleOwner(), monthlyProgressCard::setCurrentPeriodContributions);
        progressViewModel.getLastMonthContributions().observe(getViewLifecycleOwner(), monthlyProgressCard::setLastPeriodContributions);

        this.viewModel = progressViewModel;

        loadData(false);

        return root;
    }

}