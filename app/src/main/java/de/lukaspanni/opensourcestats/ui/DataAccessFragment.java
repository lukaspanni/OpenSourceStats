package de.lukaspanni.opensourcestats.ui;

import androidx.fragment.app.Fragment;

/**
 * Abstract Base Class for all Fragments that need access to data
 */
public abstract class DataAccessFragment extends Fragment implements StringResourceAccess {

    protected DataAccessViewModel viewModel;

    protected void loadData(boolean forceReload) {
        viewModel.loadData(forceReload);
    }

    @Override
    public String getStringResource(int id) {
        return getString(id);
    }
}
