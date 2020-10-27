package de.lukaspanni.opensourcestats.ui.details;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.Objects;

import de.lukaspanni.opensourcestats.MainActivity;
import de.lukaspanni.opensourcestats.util.TimeSpan;


public abstract class RepositoryListFragment  extends ListFragment {
    protected DetailsViewModel detailsViewModel;
    private int fragment_layout;

    protected RepositoryListFragment(@LayoutRes int fragment_layout){
       this.fragment_layout = fragment_layout;
    }

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

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        //Currently List-Items are Strings, change if needed
        String item = (String) l.getItemAtPosition(position);
        Bundle b = new Bundle();
        b.putString("TargetRepository", item);
        Navigation.findNavController(v).navigate(getNavigationAction(), b);

    }

    protected abstract int getNavigationAction();
}
