package com.example.opensoucestats.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.opensoucestats.R;

import java.util.Objects;

public class DashboardFragment extends ListFragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView overview = root.findViewById(R.id.text_overview);
        //final TextView repos = root.findViewById(R.id.text_repos);
        //dashboardViewModel.getOverview().observe(getViewLifecycleOwner(), s -> overview.setText(s));

        dashboardViewModel.getRepositories().observe(getViewLifecycleOwner(), strings -> setListAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.list_item, R.id.repo_list_item, strings.toArray())));

        dashboardViewModel.loadData(getActivity());

        return root;
    }
}