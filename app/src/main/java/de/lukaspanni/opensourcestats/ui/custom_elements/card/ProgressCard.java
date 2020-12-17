package de.lukaspanni.opensourcestats.ui.custom_elements.card;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import java.text.DecimalFormat;

import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.ContributionCountChange;
import de.lukaspanni.opensourcestats.ui.custom_elements.PercentageTextView;

public class ProgressCard extends CustomCard {

    private ContributionCount currentPeriodContributions;
    private ContributionCount lastPeriodContributions;
    private PercentageTextView commitCountChangeText;
    private PercentageTextView issueCountChangeText;
    private PercentageTextView pullRequestCountChangeText;
    private PercentageTextView pullRequestReviewCountChangeText;
    private ContributionCountChange change;


    public ProgressCard(@NonNull Context context) {
        super(context);
    }

    public ProgressCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProgressCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected int getCardLayout() {
        return R.layout.progress_card_component;
    }

    @Override
    protected void initViews() {
        commitCountChangeText = findViewById(R.id.commit_change);
        issueCountChangeText = findViewById(R.id.issue_change);
        pullRequestCountChangeText = findViewById(R.id.pull_request_change);
        pullRequestReviewCountChangeText = findViewById(R.id.pull_request_review_change);
    }

    public void setCurrentPeriodContributions(ContributionCount currentPeriodContributions) {
        this.currentPeriodContributions = currentPeriodContributions;
        if (this.lastPeriodContributions != null) {
            updateChange();
        }

    }

    public void setLastPeriodContributions(ContributionCount lastPeriodContributions) {
        this.lastPeriodContributions = lastPeriodContributions;
        if (this.currentPeriodContributions != null) {
            updateChange();
        }
    }


    private void updateChange() {
        this.change = new ContributionCountChange(currentPeriodContributions, lastPeriodContributions);
        updateViewPercentages();
    }

    private void updateViewPercentages() {
        commitCountChangeText.setPercentage(change.getCommitCountChange());
        issueCountChangeText.setPercentage(change.getIssueCountChange());
        pullRequestCountChangeText.setPercentage(change.getPullRequestCountChange());
        pullRequestReviewCountChangeText.setPercentage(change.getPullRequestReviewCountChange());
    }

}
