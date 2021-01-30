package de.lukaspanni.opensourcestats.ui.custom_elements.card;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.ContributionCountChange;
import de.lukaspanni.opensourcestats.ui.custom_elements.PercentageTextView;
import de.lukaspanni.opensourcestats.ui.progress.MotivationMessageFactory;
import de.lukaspanni.opensourcestats.ui.progress.MotivationMessage;

public class ProgressCard extends CustomCard {

    private ContributionCount currentPeriodContributions;
    private ContributionCount lastPeriodContributions;
    private PercentageTextView commitCountChangeText;
    private PercentageTextView issueCountChangeText;
    private PercentageTextView pullRequestCountChangeText;
    private PercentageTextView pullRequestReviewCountChangeText;
    private TextView motivationMessageText;
    private MotivationMessageFactory motivationMessageFactory;


    public ProgressCard(@NonNull Context context) {
        super(context);
        motivationMessageFactory = new MotivationMessageFactory(this);
    }

    public ProgressCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        motivationMessageFactory = new MotivationMessageFactory(this);
    }

    public ProgressCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        motivationMessageFactory = new MotivationMessageFactory(this);
    }

    public ProgressCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        motivationMessageFactory = new MotivationMessageFactory(this);
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
        motivationMessageText = findViewById(R.id.motivation_message);
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
        ContributionCountChange change = new ContributionCountChange(currentPeriodContributions, lastPeriodContributions);
        updateViewPercentages(change);
        sendMotivationMessage(change);
    }

    private void updateViewPercentages(ContributionCountChange change) {
        commitCountChangeText.setPercentage(change.getCommitCountChange());
        issueCountChangeText.setPercentage(change.getIssueCountChange());
        pullRequestCountChangeText.setPercentage(change.getPullRequestCountChange());
        pullRequestReviewCountChangeText.setPercentage(change.getPullRequestReviewCountChange());
    }

    private void sendMotivationMessage(ContributionCountChange change){
        MotivationMessage message = motivationMessageFactory.createMotivationMessage(change);
        motivationMessageText.setText(message.getMessage());
    }
}
