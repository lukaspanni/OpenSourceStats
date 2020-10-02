package com.example.opensoucestats.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.opensoucestats.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView overview = root.findViewById(R.id.text_overview);
        final TextView repos = root.findViewById(R.id.text_repos);
        dashboardViewModel.getOverview().observe(getViewLifecycleOwner(), s -> overview.setText(s));
        dashboardViewModel.getRepositories().observe(getViewLifecycleOwner(), s -> repos.setText(s));

        dashboardViewModel.loadData(getActivity());

        return root;
    }
}