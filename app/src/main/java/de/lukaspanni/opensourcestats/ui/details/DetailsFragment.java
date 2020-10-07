package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lukaspanni.opensourcestats.R;


public class DetailsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        view.findViewById(R.id.to_commit_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_commit_repos));
        view.findViewById(R.id.to_issue_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_issue_repos));
        view.findViewById(R.id.to_pull_request_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_pullrequest_repos));
        view.findViewById(R.id.to_pull_request_review_repos).setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_details_to_pullrequest_review_repos));

        return view;
    }
}