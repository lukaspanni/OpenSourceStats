package de.lukaspanni.opensourcestats.ui.custom_elements.card;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import java.util.Date;

import de.lukaspanni.opensourcestats.data.TimeSpan;

public class AnalysisCard extends CustomCard {

    private TextView mostTotalContributionsText;
    private TextView mostCommitsText;

    public AnalysisCard(@NonNull Context context) {
        super(context);
    }

    public AnalysisCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnalysisCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnalysisCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getCardLayout() {
        return R.layout.anaylsis_card_component;
    }

    @Override
    protected void initViews() {
        mostTotalContributionsText = findViewById(R.id.most_total_contributions);
        mostCommitsText = findViewById(R.id.most_commits);
    }

    public void setMostTotalContributionsDay(TimeSpan timeSpan){
        if(timeSpan != null)
            mostTotalContributionsText.setText(TimeSpan.dateFormattedString(timeSpan.getStart()));
    }

    public void setMostCommitsText(TimeSpan timeSpan){
        if(timeSpan != null)
            mostCommitsText.setText(TimeSpan.dateFormattedString(timeSpan.getStart()));
    }
}
