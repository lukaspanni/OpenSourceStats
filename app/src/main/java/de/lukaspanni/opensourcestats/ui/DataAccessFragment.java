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
        viewModel.loadData(forceReload);
    }
}
