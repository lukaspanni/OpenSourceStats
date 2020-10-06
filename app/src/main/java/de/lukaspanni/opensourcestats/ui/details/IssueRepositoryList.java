package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lukaspanni.opensourcestats.R;

import java.util.Objects;


public class IssueRepositoryList extends RepositoryListFragment {

    public IssueRepositoryList(){
        super(R.layout.fragment_issue_repository_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        detailsViewModel.getIssueRepositories().observe(getViewLifecycleOwner(), strings -> setListAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.repository_list_item, R.id.repo_list_item, strings.toArray())));
        return v;
    }


}