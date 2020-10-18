package de.lukaspanni.opensourcestats;

import androidx.annotation.NonNull;

import de.lukaspanni.opensourcestats.auth.AuthHandler;

public interface DataAccessViewModel {
    void loadData(@NonNull AuthHandler handler, boolean forceReload);
}
