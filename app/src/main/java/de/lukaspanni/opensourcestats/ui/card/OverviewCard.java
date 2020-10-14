package de.lukaspanni.opensourcestats.ui.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.UserContributionsQuery;
import de.lukaspanni.opensourcestats.client.ContributionCount;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;

public class OverviewCard extends LinearLayout {

    private TextView commitCountText;
    private TextView issueCountText;
    private TextView pullRequestCountText;
    private TextView pullRequestReviewCountText;

    public OverviewCard(@NonNull Context context) {
        this(context, null);
    }

    public OverviewCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        applyAttributes(attrs);
    }

    public OverviewCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        applyAttributes(attrs);
    }

    public OverviewCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        applyAttributes(attrs);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.card_component, this);
        commitCountText = findViewById(R.id.commit_count);
        issueCountText = findViewById(R.id.issue_count);
        pullRequestCountText = findViewById(R.id.pull_request_count);
        pullRequestReviewCountText = findViewById(R.id.pull_request_review_count);
    }

    public void setCommitCount(int commitCount) {
        this.commitCountText.setText(String.valueOf(commitCount));
    }

    public void setIssueCount(int issueCount) {
        this.issueCountText.setText(String.valueOf(issueCount));
    }

    public void setPullRequestCount(int pullRequestCount) {
        this.pullRequestCountText.setText(String.valueOf(pullRequestCount));
    }

    public void setPullRequestReviewCount(int pullRequestReviewCount) {
        this.pullRequestReviewCountText.setText(String.valueOf(pullRequestReviewCount));
    }

    public void setContributions(ContributionCount contributions) {
        setCommitCount(contributions.getCommitCount());
        setIssueCount(contributions.getIssueCount());
        setPullRequestCount(contributions.getPullRequestCount());
        setPullRequestReviewCount(contributions.getPullRequestReviewCount());
    }


    private void applyAttributes(AttributeSet attrs) {
        TextView titleView = findViewById(R.id.overview_title);
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.OverviewCard);
        titleView.setText(arr.getText(R.styleable.OverviewCard_android_text).toString());
        arr.recycle();
    }


}
