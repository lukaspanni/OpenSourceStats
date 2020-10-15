package de.lukaspanni.opensourcestats.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.MainActivity;
import de.lukaspanni.opensourcestats.ui.card.OverviewCard;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
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

        dashboardViewModel.getCurrentWeekContributions().observe(getViewLifecycleOwner(), currentWeekCard::setContributions);
        dashboardViewModel.getLastWeekContributions().observe(getViewLifecycleOwner(), lastWeekCard::setContributions);

        dashboardViewModel.getCurrentMonthContributions().observe(getViewLifecycleOwner(), currentMonthCard::setContributions);
        dashboardViewModel.getLastMonthContributions().observe(getViewLifecycleOwner(), lastMonthCard::setContributions);

        loadData(false);
        return root;
    }

    private void loadData(boolean forceReload) {
        //Only allow use from MainActivity because it holds a Client instance
        Activity parentActivity = getActivity();
        assert parentActivity != null;
        if (parentActivity.getClass() == MainActivity.class) {
            dashboardViewModel.loadData(((MainActivity) parentActivity).getAuthHandler(), forceReload);
        } else {
            throw new UnsupportedOperationException("Cannot use GHClient from other Activity");
        }

    }
}