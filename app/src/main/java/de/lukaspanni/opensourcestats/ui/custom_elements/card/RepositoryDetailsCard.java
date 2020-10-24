package de.lukaspanni.opensourcestats.ui.custom_elements.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lukaspanni.opensourcestats.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.Set;

public class RepositoryDetailsCard extends CustomCard {
    private TextView repositoryName;
    private TextView repositoryDescription;
    private TextView repositoryAccess;
    private TextView repositoryPrimaryLanguage;
    private ListView repositoryLanguages;
    private TextView repositoryCreatedAt;

    private TextView repositoryLanguagesLabel;

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
    protected int getCardLayout() {
        return R.layout.repo_detail_card_component;
    }

    @Override
    protected void initViews() {
        repositoryName = findViewById(R.id.repository_name);
        repositoryDescription = findViewById(R.id.repository_description);
        repositoryAccess = findViewById(R.id.repository_access);
        repositoryPrimaryLanguage = findViewById(R.id.repository_primary_language);
        repositoryLanguages = findViewById(R.id.repository_languages);
        repositoryLanguagesLabel = findViewById(R.id.repository_languages_label);
        repositoryCreatedAt = findViewById(R.id.repository_created_at);
    }

    public void setRepositoryName(String name) {
        this.repositoryName.setText(name);
    }

    public void setRepositoryDescription(String description) {
        this.repositoryDescription.setText(description);
    }

    public void setRepositoryAccess(boolean isPrivate) {
        if (isPrivate) {
            this.repositoryAccess.setText(getContext().getString(R.string.repository_access_private));
            this.repositoryAccess.setVisibility(TextView.VISIBLE);
        } else {
            this.repositoryAccess.setVisibility(TextView.GONE);
        }
    }

    public void setRepositoryPrimaryLanguage(String language) {
        this.repositoryPrimaryLanguage.setText(language);
    }

    public void setRepositoryCreatedAt(Date createdAt) {
        DateFormat formatter = DateFormat.getDateInstance();
        String createdAtFormatted = formatter.format(createdAt);
        this.repositoryCreatedAt.setText(createdAtFormatted);
    }

    public void setRepositoryLanguages(Set<String> languages) {
        if(languages.size() > 1) {
            String[] languageArray = languages.toArray(new String[languages.size()]);
            ArrayAdapter<String> languagesAdapter = new ArrayAdapter<>(getContext(), R.layout.language_list_item, R.id.language_list_item, languageArray);
            this.repositoryLanguages.setAdapter(languagesAdapter);
            this.repositoryLanguages.setVisibility(ListView.VISIBLE);
            this.repositoryLanguagesLabel.setVisibility(TextView.VISIBLE);
        }else{
            this.repositoryLanguages.setVisibility(ListView.GONE);
            this.repositoryLanguagesLabel.setVisibility(TextView.GONE);
        }

    }
}
