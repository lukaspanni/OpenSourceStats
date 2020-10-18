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
import de.lukaspanni.opensourcestats.ui.card.ProgressCard;

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

        ProgressCard progressOverviewCard = root.findViewById(R.id.progress_card);

        progressViewModel.getCurrentWeekContributions().observe(getViewLifecycleOwner(), progressOverviewCard::setCurrentPeriodContributions);
        progressViewModel.getLastWeekContributions().observe(getViewLifecycleOwner(), progressOverviewCard::setLastPeriodContributions);

        this.viewModel = progressViewModel;

        loadData(true);

        return root;
    }

}