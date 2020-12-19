package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LiveData;

import com.lukaspanni.opensourcestats.R;

import java.util.List;

public class PullRequestRepositoryList extends RepositoryListFragment {

    public PullRequestRepositoryList() {
        super(R.layout.fragment_pull_request_repository_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected LiveData<List<String>> getObservableList() {
        return detailsViewModel.getPullRequestRepositories();
    }

    @Override
    protected int getNavigationAction() {
        return R.id.action_pullrequest_repos_to_repository_details;
    }


}