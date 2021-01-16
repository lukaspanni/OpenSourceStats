package de.lukaspanni.opensourcestats.ui.details;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.Set;

import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;

import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.repository.RepositoryDataRepository;
import de.lukaspanni.opensourcestats.data.RepositoryName;

public class RepositoryDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Date> repositoryCreatedAt;
    private MutableLiveData<String> repositoryName;
    private MutableLiveData<String> repositoryDescription;
    private MutableLiveData<String> repositoryPrimaryLanguage;
    private MutableLiveData<Set<String>> repositoryLanguages;
    private MutableLiveData<Boolean> repositoryIsPrivate;


    public RepositoryDetailsViewModel(Application app) {
        super(app);
        repositoryCreatedAt = new MutableLiveData<>();
        repositoryName = new MutableLiveData<>();
        repositoryDescription = new MutableLiveData<>();
        repositoryPrimaryLanguage = new MutableLiveData<>();
        repositoryLanguages = new MutableLiveData<>();
        repositoryIsPrivate = new MutableLiveData<>();
    }


    public MutableLiveData<Date> getRepositoryCreatedAt() {
        return repositoryCreatedAt;
    }

    public MutableLiveData<String> getRepositoryName() {
        return repositoryName;
    }

    public MutableLiveData<String> getRepositoryDescription() {
        return repositoryDescription;
    }

    public MutableLiveData<String> getRepositoryPrimaryLanguage() {
        return repositoryPrimaryLanguage;
    }

    public MutableLiveData<Set<String>> getRepositoryLanguages() {
        return repositoryLanguages;
    }

    public MutableLiveData<Boolean> getRepositoryIsPrivate() {
        return repositoryIsPrivate;
    }


    public void loadData(String repositoryWithOwner, boolean forceReload) {
        this.repositoryName.postValue(repositoryWithOwner);
        OpenSourceStatsApplication app = (OpenSourceStatsApplication) getApplication();
        RepositoryDataRepository repository = app.getRepositoryDataRepository();
        repository.loadRepositoryData(new RepositoryName(repositoryWithOwner), response -> {
            RepositoryDataResponse repositoryData = (RepositoryDataResponse) response;
            this.repositoryCreatedAt.postValue(repositoryData.getCreatedAt());
            this.repositoryDescription.postValue(repositoryData.getDescription());
            this.repositoryPrimaryLanguage.postValue(repositoryData.getPrimaryLanguage());
            this.repositoryLanguages.postValue(repositoryData.getLanguages());
            this.repositoryIsPrivate.postValue(repositoryData.isPrivate());
        }, forceReload);

    }


}
