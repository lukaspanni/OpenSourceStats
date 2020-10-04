package de.lukaspanni.opensourcestats.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.lukaspanni.opensourcestats.R;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView commitCount = root.findViewById(R.id.commit_count);
        TextView issueCount = root.findViewById(R.id.issue_count);
        TextView pullRequestCount = root.findViewById(R.id.pull_request_count);
        TextView pullRequestReviewCount = root.findViewById(R.id.pull_request_review_count);

        dashboardViewModel.getCommitCount().observe(getViewLifecycleOwner(), count -> commitCount.setText(String.valueOf(count)));
        dashboardViewModel.getIssueCount().observe(getViewLifecycleOwner(), count -> issueCount.setText(String.valueOf(count)));
        dashboardViewModel.getPullRequestCount().observe(getViewLifecycleOwner(), count -> pullRequestCount.setText(String.valueOf(count)));
        dashboardViewModel.getPullRequestReviewCount().observe(getViewLifecycleOwner(), count -> pullRequestReviewCount.setText(String.valueOf(count)));
        dashboardViewModel.loadData(getActivity());

        return root;
    }
}