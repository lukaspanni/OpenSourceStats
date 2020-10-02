package com.example.opensoucestats.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.opensoucestats.R;
import com.example.opensoucestats.auth.AuthHandler;
import com.example.opensoucestats.client.ClientDataCallback;
import com.example.opensoucestats.client.GHClient;
import com.example.opensoucestats.client.ResponseData;
import com.example.opensoucestats.client.UserContributionsResponse;

import java.util.Date;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        AuthHandler handler = AuthHandler.getInstance(getActivity());
        if (handler.checkAuth()) {
            GHClient client = new GHClient(handler);
            Date start = new Date(System.currentTimeMillis() - (7 * 1000 * 60 * 60 * 24));
            Date end = new Date();
            client.loadData(start, end, new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    getActivity().runOnUiThread(() -> {
                        StringBuilder outBuilder = new StringBuilder("Current Week:\n");
                        outBuilder.append("Commits: " + responseData.getCommits() + "\n");
                        outBuilder.append("Issues: " + responseData.getIssues() + "\n");
                        outBuilder.append("PullRequests: " + responseData.getPullRequests() + "\n");
                        textView.setText(outBuilder.toString());
                    });
                }
            });
        }

        return root;
    }
}