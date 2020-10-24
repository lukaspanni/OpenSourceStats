package de.lukaspanni.opensourcestats.ui.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.Set;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.RepositoryDataClient;
import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;

public class RepositoryDetailsViewModel extends ViewModel {

    private MutableLiveData<Date> repositoryCreatedAt;
    private MutableLiveData<String> repositoryName;
    private MutableLiveData<String> repositoryDescription;
    private MutableLiveData<String> repositoryPrimaryLanguage;
    private MutableLiveData<Set<String>> repositoryLanguages;
    private MutableLiveData<Boolean> repositoryIsPrivate;
    private RepositoryDataClient client;


    public RepositoryDetailsViewModel(){
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


    public void loadData(String repository, String owner, @NonNull AuthHandler handler){
        this.repositoryName.postValue(repository);
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.repositoryData(repository, owner, response -> {
                RepositoryDataResponse repositoryData = (RepositoryDataResponse) response;
                this.repositoryCreatedAt.postValue(repositoryData.getCreatedAt());
                this.repositoryDescription.postValue(repositoryData.getDescription());
                this.repositoryPrimaryLanguage.postValue(repositoryData.getPrimaryLanguage());
                this.repositoryLanguages.postValue(repositoryData.getLanguages());
                this.repositoryIsPrivate.postValue(repositoryData.isPrivate());
            });
        }
    }



}
