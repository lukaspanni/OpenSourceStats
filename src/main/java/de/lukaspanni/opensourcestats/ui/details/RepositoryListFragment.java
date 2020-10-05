package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import com.lukaspanni.opensourcestats.R;


public class RepositoryListFragment  extends ListFragment {
    protected DetailsViewModel detailsViewModel;
    private int fragment_layout;

    protected RepositoryListFragment(@LayoutRes int fragment_layout){
        this.fragment_layout = fragment_layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailsViewModel =
                ViewModelProviders.of(this).get(DetailsViewModel.class);
        View view = inflater.inflate(this.fragment_layout, container, false);
        detailsViewModel.loadData(getActivity());
        return view;
    }

}
