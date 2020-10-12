package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.util.DateUtility;
import de.lukaspanni.opensourcestats.util.TimeSpan;


public class TimeSpanDetails extends Fragment {

    private TimeSpan timeSpan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_week_details, container, false);
        view.findViewById(R.id.to_commit_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_commit_repos, getArguments()));
        view.findViewById(R.id.to_issue_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_issue_repos, getArguments()));
        view.findViewById(R.id.to_pull_request_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_pullrequest_repos, getArguments()));
        view.findViewById(R.id.to_pull_request_review_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_pullrequest_review_repos, getArguments()));

        if (getArguments() != null && getArguments().get("timeSpan") != null) {
            timeSpan = getArguments().getParcelable("timeSpan");
        }

        if (timeSpan != null) {
            TextView header = view.findViewById(R.id.week_header);
            header.setText(DateUtility.toTimeSpanString(timeSpan));
        }

        return view;
    }

}