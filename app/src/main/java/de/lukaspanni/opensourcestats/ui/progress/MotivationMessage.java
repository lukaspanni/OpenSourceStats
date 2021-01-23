package de.lukaspanni.opensourcestats.ui.progress;


import java.util.Objects;

/**
 * Motivation-Message Value-Object //TODO: Add to ValueObject documentation
 */
public final class MotivationMessage {

    public enum MOTIVATION_TYPE {
        KEEP_ON, WORK_HARDER
    }

    private final String message;
    private final MOTIVATION_TYPE type;

    public MotivationMessage(String message, MOTIVATION_TYPE type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public MOTIVATION_TYPE getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotivationMessage that = (MotivationMessage) o;
        return Objects.equals(getMessage(), that.getMessage()) && getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getType());
    }
}
