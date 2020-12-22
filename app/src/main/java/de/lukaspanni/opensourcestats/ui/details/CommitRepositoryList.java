package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LiveData;

import com.lukaspanni.opensourcestats.R;

import java.util.List;


public class CommitRepositoryList extends RepositoryListFragment {

    public CommitRepositoryList() {
        super(R.layout.fragment_commit_repository_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected LiveData<List<String>> getObservableList() {
        return detailsViewModel.getCommitRepositories();
    }

    @Override
    protected int getNavigationAction() {
        return R.id.action_commit_repos_to_repository_details;
    }
}