package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lukaspanni.opensourcestats.R;

import java.util.Objects;

import de.lukaspanni.opensourcestats.MainActivity;
import de.lukaspanni.opensourcestats.ui.custom_elements.card.RepositoryDetailsCard;


public class RepositoryDetailsFragment extends Fragment {

    private RepositoryDetailsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Objects.requireNonNull(getActivity()).onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(RepositoryDetailsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_repository_details, container, false);

        if (getArguments() == null)
            return view;


        RepositoryDetailsCard detailsCard = view.findViewById(R.id.repository_details_card);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        ActionBar actionbar = activity.getSupportActionBar();

        viewModel.getRepositoryName().observe(getViewLifecycleOwner(), name -> {
            detailsCard.setRepositoryName(name);
            actionbar.setTitle(name);
        });

        viewModel.getRepositoryDescription().observe(getViewLifecycleOwner(), detailsCard::setRepositoryDescription);
        viewModel.getRepositoryPrimaryLanguage().observe(getViewLifecycleOwner(), detailsCard::setRepositoryPrimaryLanguage);
        viewModel.getRepositoryLanguages().observe(getViewLifecycleOwner(), detailsCard::setRepositoryLanguages);
        viewModel.getRepositoryCreatedAt().observe(getViewLifecycleOwner(), detailsCard::setRepositoryCreatedAt);
        viewModel.getRepositoryIsPrivate().observe(getViewLifecycleOwner(), detailsCard::setRepositoryAccess);


        String repoWithOwner = getArguments().getString("TargetRepository");
        if (repoWithOwner != null) {


            if (activity.getClass() == MainActivity.class) {
                viewModel.loadData(repoWithOwner, ((MainActivity) activity).getAuthHandler());
            } else {
                throw new UnsupportedOperationException("Cannot use GHClient from other Activity");
            }
        }

        return view;
    }
}