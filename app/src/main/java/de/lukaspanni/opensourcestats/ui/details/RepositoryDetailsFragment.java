package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lukaspanni.opensourcestats.R;


public class RepositoryDetailsFragment extends Fragment {

    private String owner;
    private String repository;
    private RepositoryDetailsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(RepositoryDetailsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_repository_details, container, false);



        assert getArguments() != null;
        String repoWithOwner = getArguments().getString("TargetRepository");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(repoWithOwner);

        if (repoWithOwner != null) {
            String[] split = repoWithOwner.split("/");
            owner = split[0];
            repository = split[1];
        }

        return view;
    }
}