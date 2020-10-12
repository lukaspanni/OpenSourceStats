package de.lukaspanni.opensourcestats.ui.details;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import de.lukaspanni.opensourcestats.MainActivity;
import de.lukaspanni.opensourcestats.util.TimeSpan;


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

        TimeSpan timeSpan = null;
        if (getArguments() != null && getArguments().get("timeSpan") != null) {
            timeSpan = getArguments().getParcelable("timeSpan");
        }

        //Only allow use from MainActivity because it holds a Client instance
        Activity parentActivity = getActivity();
        assert parentActivity != null;
        if(parentActivity.getClass() == MainActivity.class){
            detailsViewModel.loadData(timeSpan, ((MainActivity) parentActivity).getAuthHandler());
        }else{
            throw new UnsupportedOperationException("Cannot use RepositoryList from other Activity");
        }
        return view;
    }

}
