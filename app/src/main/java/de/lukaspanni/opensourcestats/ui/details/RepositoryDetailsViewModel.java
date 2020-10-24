package de.lukaspanni.opensourcestats.ui.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.Set;

public class RepositoryDetailsViewModel extends ViewModel {

    private MutableLiveData<Date> repositoryCreatedAt;
    private MutableLiveData<String> repositoryName;
    private MutableLiveData<String> repositoryDescription;
    private MutableLiveData<String> repositoryPrimaryLanguage;
    private MutableLiveData<Set<String>> repositoryLanguages;
    private MutableLiveData<Boolean> repositoryIsPrivate;


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


    public void loadData(){

    }

}
