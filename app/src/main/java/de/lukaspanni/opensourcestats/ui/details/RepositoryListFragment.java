package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.lukaspanni.opensourcestats.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import de.lukaspanni.opensourcestats.util.TimeSpan;


public abstract class RepositoryListFragment extends ListFragment {
    protected DetailsViewModel detailsViewModel;
    private int fragment_layout;

    protected RepositoryListFragment(@LayoutRes int fragment_layout) {
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
            requireActivity().onBackPressed();
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

        assert timeSpan != null;
        detailsViewModel.loadData(timeSpan);
        getObservableList().observe(getViewLifecycleOwner(), getListObserver());
        return view;
    }

    @NotNull
    private Observer<List<String>> getListObserver() {
        return strings -> {
            if(strings.size() < 1){
                strings.add(getString(R.string.no_repos_found_item));
            }
            setListAdapter(new ArrayAdapter<>(requireContext(), R.layout.repository_list_item, R.id.repo_list_item, strings.toArray()));
        };
    }

    protected abstract LiveData<List<String>> getObservableList();

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        //Currently List-Items are Strings, change if needed
        String item = (String) l.getItemAtPosition(position);
        if(item.equals(getString(R.string.no_repos_found_item))){
            Toast.makeText(getContext(), R.string.no_additional_information, Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle b = new Bundle();
        b.putString("TargetRepository", item);
        Navigation.findNavController(v).navigate(getNavigationAction(), b);

    }


    protected abstract int getNavigationAction();
}
