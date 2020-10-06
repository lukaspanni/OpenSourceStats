package de.lukaspanni.opensourcestats.client;



public abstract class ResponseData {

    public enum QueryType{
        USER_CONTRIBUTIONS_QUERY
    }

    private QueryType queryType;

    public ResponseData(QueryType queryType){
        this.queryType = queryType;
    }
}
