package de.lukaspanni.opensourcestats.ui.card;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.client.ContributionCount;

public class OverviewCard extends CustomCard {

    private TextView commitCountText;
    private TextView issueCountText;
    private TextView pullRequestCountText;
    private TextView pullRequestReviewCountText;

    public OverviewCard(@NonNull Context context) {
        super(context);
    }

    public OverviewCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OverviewCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverviewCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    protected void initViews() {
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



}
