package de.lukaspanni.opensourcestats.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.ui.DataAccessFragment;
import de.lukaspanni.opensourcestats.ui.custom_elements.card.AnalysisCard;
import de.lukaspanni.opensourcestats.ui.custom_elements.card.OverviewCard;


public class DashboardFragment extends DataAccessFragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SwipeRefreshLayout refresh = root.findViewById(R.id.dashboard_refresh);
        refresh.setOnRefreshListener(() -> {
            loadData(true);
            refresh.setRefreshing(false);
        });

        OverviewCard currentWeekCard = root.findViewById(R.id.current_week_card);
        OverviewCard lastWeekCard = root.findViewById(R.id.last_week_card);

        OverviewCard currentMonthCard = root.findViewById(R.id.current_month_card);
        OverviewCard lastMonthCard = root.findViewById(R.id.last_month_card);

        AnalysisCard currentWeekAnalysisCard = root.findViewById(R.id.current_week_analysis_card);


        dashboardViewModel.getCurrentWeekContributions().observe(getViewLifecycleOwner(), currentWeekCard::setContributions);
        dashboardViewModel.getLastWeekContributions().observe(getViewLifecycleOwner(), lastWeekCard::setContributions);

        dashboardViewModel.getCurrentMonthContributions().observe(getViewLifecycleOwner(), currentMonthCard::setContributions);
        dashboardViewModel.getLastMonthContributions().observe(getViewLifecycleOwner(), lastMonthCard::setContributions);

        dashboardViewModel.getMostTotalContributions().observe(getViewLifecycleOwner(),
                contributionCount -> currentWeekAnalysisCard.setMostTotalContributionsDay(contributionCount.getContributionTimeSpan()));
        dashboardViewModel.getMostCommits().observe(getViewLifecycleOwner(),
                contributionCount -> currentWeekAnalysisCard.setMostCommitsText(contributionCount.getContributionTimeSpan()));

        this.viewModel = dashboardViewModel;

        loadData(false);
        return root;
    }

}