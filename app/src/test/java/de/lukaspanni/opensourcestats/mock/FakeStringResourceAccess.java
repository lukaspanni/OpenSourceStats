package de.lukaspanni.opensourcestats.mock;

import de.lukaspanni.opensourcestats.ui.StringResourceAccess;

public class FakeStringResourceAccess implements StringResourceAccess {
    @Override
    public String getStringResource(int id) {
        return "";
    }
}
