package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lukaspanni.opensourcestats.R;

import java.util.Objects;

public class PullRequestRepositoryList extends RepositoryListFragment {

    public PullRequestRepositoryList(){
        super(R.layout.fragment_pull_request_repository_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        detailsViewModel.getPullRequestRepositories().observe(getViewLifecycleOwner(), strings -> setListAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.repository_list_item, R.id.repo_list_item, strings.toArray())));
        return v;
    }

    @Override
    protected int getNavigationAction() {
        return R.id.action_pullrequest_repos_to_repository_details;
    }


}