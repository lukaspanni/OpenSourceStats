package de.lukaspanni.opensourcestats.ui.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.MainActivity;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView cwCommitCount = root.findViewById(R.id.current_week_commit_count);
        TextView cwIssueCount = root.findViewById(R.id.current_week_issue_count);
        TextView cwPullRequestCount = root.findViewById(R.id.current_week_pull_request_count);
        TextView cwPullRequestReviewCount = root.findViewById(R.id.current_week_pull_request_review_count);
        TextView lwCommitCount = root.findViewById(R.id.last_week_commit_count);
        TextView lwIssueCount = root.findViewById(R.id.last_week_issue_count);
        TextView lwPullRequestCount = root.findViewById(R.id.last_week_pull_request_count);
        TextView lwPullRequestReviewCount = root.findViewById(R.id.last_week_pull_request_review_count);

        dashboardViewModel.getCurrentWeekCommitCount().observe(getViewLifecycleOwner(), count -> cwCommitCount.setText(String.valueOf(count)));
        dashboardViewModel.getCurrentWeekIssueCount().observe(getViewLifecycleOwner(), count -> cwIssueCount.setText(String.valueOf(count)));
        dashboardViewModel.getCurrentWeekPullRequestCount().observe(getViewLifecycleOwner(), count -> cwPullRequestCount.setText(String.valueOf(count)));
        dashboardViewModel.getCurrentWeekPullRequestReviewCount().observe(getViewLifecycleOwner(), count -> cwPullRequestReviewCount.setText(String.valueOf(count)));

        dashboardViewModel.getLastWeekCommitCount().observe(getViewLifecycleOwner(), count -> lwCommitCount.setText(String.valueOf(count)));
        dashboardViewModel.getLastWeekIssueCount().observe(getViewLifecycleOwner(), count -> lwIssueCount.setText(String.valueOf(count)));
        dashboardViewModel.getLastWeekPullRequestCount().observe(getViewLifecycleOwner(), count -> lwPullRequestCount.setText(String.valueOf(count)));
        dashboardViewModel.getLastWeekPullRequestReviewCount().observe(getViewLifecycleOwner(), count -> lwPullRequestReviewCount.setText(String.valueOf(count)));

        //TODO: Extract shared Code
        //Only allow use from MainActivity because it holds a Client instance
        Activity parentActivity = getActivity();
        assert parentActivity != null;
        if(parentActivity.getClass() == MainActivity.class){
            dashboardViewModel.loadData(((MainActivity) parentActivity).getAuthHandler());
        }else{
            throw new UnsupportedOperationException("Cannot use RepositoryList from other Activity");
        }

        return root;
    }
}