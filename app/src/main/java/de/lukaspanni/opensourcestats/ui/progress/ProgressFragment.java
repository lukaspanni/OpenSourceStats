package de.lukaspanni.opensourcestats.ui.progress;

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

import java.text.DecimalFormat;

import de.lukaspanni.opensourcestats.MainActivity;

public class ProgressFragment extends Fragment {

    private ProgressViewModel progressViewModel;
    private DecimalFormat decimalFormat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);

        decimalFormat = new DecimalFormat("##.##%");

        TextView weeklyCommitGain = root.findViewById(R.id.progress_week_commits);
        TextView weeklyIssueGain = root.findViewById(R.id.progress_week_issues);
        TextView weeklyPullRequestGain = root.findViewById(R.id.progress_week_pull_requests);
        TextView weeklyPullRequestReviewGain = root.findViewById(R.id.progress_week_pull_request_reviews);

        progressViewModel.getWeeklyCommitGain().observe(getViewLifecycleOwner(), gain -> weeklyCommitGain.setText(getFormattedPercentString(gain)));
        progressViewModel.getWeeklyIssueGain().observe(getViewLifecycleOwner(), gain -> weeklyIssueGain.setText(getFormattedPercentString(gain)));
        progressViewModel.getWeeklyPullRequestGain().observe(getViewLifecycleOwner(), gain -> weeklyPullRequestGain.setText(getFormattedPercentString(gain)));
        progressViewModel.getWeeklyPullRequestReviewGain().observe(getViewLifecycleOwner(), gain -> weeklyPullRequestReviewGain.setText(getFormattedPercentString(gain)));

        Activity parentActivity = getActivity();
        assert parentActivity != null;
        if(parentActivity.getClass() == MainActivity.class){
            progressViewModel.loadData(((MainActivity) parentActivity).getAuthHandler());
        }else{
            throw new UnsupportedOperationException("Cannot use RepositoryList from other Activity");
        }

        return root;
    }

    private String getFormattedPercentString(float absolute){
        return decimalFormat.format(absolute);
    }
}