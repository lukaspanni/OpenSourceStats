package de.lukaspanni.opensourcestats.ui.custom_elements.card;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import java.text.DecimalFormat;

import de.lukaspanni.opensourcestats.client.ContributionCount;
import de.lukaspanni.opensourcestats.ui.custom_elements.PercentageTextView;

public class ProgressCard extends CustomCard {

    private ContributionCount currentPeriodContributions;
    private ContributionCount lastPeriodContributions;
    private PercentageTextView commitGainText;
    private PercentageTextView issueGainText;
    private PercentageTextView pullRequestGainText;
    private PercentageTextView pullRequestReviewGainText;


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
        commitGainText = findViewById(R.id.commit_gain);
        issueGainText = findViewById(R.id.issue_gain);
        pullRequestGainText = findViewById(R.id.pull_request_gain);
        pullRequestReviewGainText = findViewById(R.id.pull_request_review_gain);
    }

    public void setCurrentPeriodContributions(ContributionCount currentPeriodContributions) {
        this.currentPeriodContributions = currentPeriodContributions;
        if (this.lastPeriodContributions != null) {
            updateGain();
        }

    }

    public void setLastPeriodContributions(ContributionCount lastPeriodContributions) {
        this.lastPeriodContributions = lastPeriodContributions;
        if (this.currentPeriodContributions != null) {
            updateGain();
        }
    }


    private void updateGain() {
        DecimalFormat decimalFormat = new DecimalFormat("##.##%");

        float commitGain = calculateGain(currentPeriodContributions.getCommitCount(), lastPeriodContributions.getCommitCount());
        float issueGain = calculateGain(currentPeriodContributions.getIssueCount(), lastPeriodContributions.getIssueCount());
        float pullRequestGain = calculateGain(currentPeriodContributions.getPullRequestCount(), lastPeriodContributions.getPullRequestCount());
        float pullRequestReviewGain = calculateGain(currentPeriodContributions.getPullRequestReviewCount(), lastPeriodContributions.getPullRequestReviewCount());

        commitGainText.setPercentage(commitGain);
        issueGainText.setPercentage(issueGain);
        pullRequestGainText.setPercentage(pullRequestGain);
        pullRequestReviewGainText.setPercentage(pullRequestReviewGain);
    }

    private float calculateGain(float current, float old) {
        if (old == 0) return 0;
        return (current / (float) old) - 1;
    }


}
