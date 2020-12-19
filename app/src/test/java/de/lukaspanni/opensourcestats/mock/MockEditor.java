package de.lukaspanni.opensourcestats.mock;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.AbstractMap;
import java.util.Set;

public class MockEditor implements SharedPreferences.Editor {
    private boolean applied;
    private AbstractMap.SimpleEntry<String, String> putStringContent;

    public boolean isApplied() {
        return applied;
    }

    public AbstractMap.SimpleEntry<String, String> getPutStringContent() {
        return putStringContent;
    }

    @Override
    public SharedPreferences.Editor putString(String key, @Nullable String value) {
        putStringContent = new AbstractMap.SimpleEntry<>(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, @Nullable Set<String> values) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return null;
    }

    @Override
    public SharedPreferences.Editor remove(String key) {
        return null;
    }

    @Override
    public SharedPreferences.Editor clear() {
        return null;
    }

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public void apply() {
        this.applied = true;
    }
}
