package de.lukaspanni.opensourcestats.ui;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import de.lukaspanni.opensourcestats.MainActivity;

/**
 * Abstract Base Class for all Fragments that need access to data
 */
public abstract class DataAccessFragment extends Fragment {

    protected DataAccessViewModel viewModel;

    protected void loadData(boolean forceReload) {
        //Only allow use from MainActivity because it holds a Client instance
        Activity parentActivity = getActivity();
        assert parentActivity != null;
        if (parentActivity.getClass() == MainActivity.class) {
            viewModel.loadData(((MainActivity) parentActivity).getAuthHandler(), forceReload);
        } else {
            throw new UnsupportedOperationException("Cannot use GHClient from other Activity");
        }

    }
}
