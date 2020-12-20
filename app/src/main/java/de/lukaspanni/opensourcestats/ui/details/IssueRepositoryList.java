package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LiveData;

import com.lukaspanni.opensourcestats.R;

import java.util.List;


public class IssueRepositoryList extends RepositoryListFragment {

    public IssueRepositoryList() {
        super(R.layout.fragment_issue_repository_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected LiveData<List<String>> getObservableList() {
        return detailsViewModel.getIssueRepositories();
    }

    @Override
    protected int getNavigationAction() {
        return R.id.action_issue_repos_to_repository_details;
    }


}