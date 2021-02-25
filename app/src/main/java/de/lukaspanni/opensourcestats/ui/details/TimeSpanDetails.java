package de.lukaspanni.opensourcestats.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lukaspanni.opensourcestats.R;

import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.data.TimeSpan;


public class TimeSpanDetails extends Fragment {

    private TimeSpan timeSpan;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_span_details, container, false);
        view.findViewById(R.id.to_commit_repos).setOnClickListener(getClickListener(view, R.id.action_details_to_commit_repos));
        view.findViewById(R.id.to_issue_repos).setOnClickListener(getClickListener(view, R.id.action_details_to_issue_repos));
        view.findViewById(R.id.to_pull_request_repos).setOnClickListener(getClickListener(view, R.id.action_details_to_pullrequest_repos));
        view.findViewById(R.id.to_pull_request_review_repos).setOnClickListener(getClickListener(view, R.id.action_details_to_pullrequest_review_repos));

        if (getArguments() != null && getArguments().get("timeSpan") != null) {
            timeSpan = getArguments().getParcelable("timeSpan");
        }

        if (timeSpan != null) {
            TextView header = view.findViewById(R.id.time_span_header);
            header.setText(timeSpan.toFormattedString());
        }

        return view;
    }

    @NotNull
    private View.OnClickListener getClickListener(View view, int resource) {
        return v -> Navigation.findNavController(view).navigate(resource, getArguments());
    }

}