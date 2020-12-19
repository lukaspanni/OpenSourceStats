package de.lukaspanni.opensourcestats.mock;

import java.util.Objects;

import de.lukaspanni.opensourcestats.data.ResponseData;

public class FakeResponseData extends ResponseData {

    private int data;

    public FakeResponseData(int data) {
        super(QueryType.NONE);
        this.data = data;
    }

    public int getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakeResponseData that = (FakeResponseData) o;
        return getData() == that.getData();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData());
    }
}
