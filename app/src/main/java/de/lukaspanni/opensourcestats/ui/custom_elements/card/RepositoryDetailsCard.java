package de.lukaspanni.opensourcestats.ui.custom_elements.card;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import java.text.DateFormat;
import java.util.Date;

public class RepositoryDetailsCard extends CustomCard {
    private TextView repositoryName;
    private TextView repositoryDescription;
    private TextView repositoryAccess;
    private TextView repositoryCreatedAt;

    public RepositoryDetailsCard(@NonNull Context context) {
        super(context);
    }

    public RepositoryDetailsCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RepositoryDetailsCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RepositoryDetailsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void initViews() {
        repositoryName = findViewById(R.id.repository_name);
        repositoryDescription = findViewById(R.id.repository_description);
        repositoryAccess = findViewById(R.id.repository_access);
        repositoryCreatedAt = findViewById(R.id.repository_created_at);
    }

    public void setRepositoryName(String name){
        this.repositoryName.setText(name);
    }

    public void setRepositoryDescription(String description){
        this.repositoryDescription.setText(description);
    }

    public void setRepositoryAccess(boolean isPrivate){
        if(isPrivate) {
            this.repositoryAccess.setText(getContext().getString(R.string.repository_access_private));
            this.repositoryAccess.setVisibility(TextView.VISIBLE);
        }else{
            this.repositoryAccess.setVisibility(TextView.GONE);
        }
    }

    public void setRepositoryCreatedAt(Date createdAt){
        DateFormat formatter = DateFormat.getDateInstance();
        String createdAtFormatted = formatter.format(createdAt);
        this.repositoryCreatedAt.setText(createdAtFormatted);
    }
}
