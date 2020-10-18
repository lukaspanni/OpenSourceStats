package de.lukaspanni.opensourcestats.ui;

import androidx.annotation.NonNull;

import de.lukaspanni.opensourcestats.auth.AuthHandler;

public interface DataAccessViewModel {
    void loadData(@NonNull AuthHandler handler, boolean forceReload);
}
