package de.lukaspanni.opensourcestats.data;



public abstract class ResponseData {

    public enum QueryType{
        USER_CONTRIBUTIONS_QUERY,
        REPOSITORY_DATA_QUERY
    }

    private QueryType queryType;

    public ResponseData(QueryType queryType){
        this.queryType = queryType;
    }
}
